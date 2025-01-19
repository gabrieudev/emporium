package br.com.gabrieudev.emporium.infrastructrure.gateways;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceUpdateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductUpdateParams;

import br.com.gabrieudev.emporium.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.emporium.application.exceptions.TransactionFailedException;
import br.com.gabrieudev.emporium.application.gateways.ProductGateway;
import br.com.gabrieudev.emporium.domain.entities.Product;
import br.com.gabrieudev.emporium.infrastructrure.persistence.models.ProductModel;
import br.com.gabrieudev.emporium.infrastructrure.persistence.repositories.ProductRepository;

public class ProductServiceGateway implements ProductGateway {
    private final ProductRepository productRepository;
    
    public ProductServiceGateway(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @CacheEvict(value = "Products", key = "#id")
    @Transactional
    public void delete(UUID id) {
        Product product = findById(id);

        productRepository.deleteById(product.getId());

        try {
            ProductUpdateParams productUpdateParams = ProductUpdateParams.builder()
                    .setActive(false)
                    .build();
    
            com.stripe.model.Product stripeProduct = com.stripe.model.Product.retrieve(product.getStripeId());
            
            stripeProduct.update(productUpdateParams);
    
        } catch (StripeException e) {
            throw new TransactionFailedException("Erro ao arquivar o produto no Stripe: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return productRepository.existsById(id);
    }

    @Override
    @Cacheable(value = "Products", key = "#id")
    @Transactional(readOnly = true)
    public Product findById(UUID id) {
        return productRepository.findById(id)
                .map(ProductModel::toDomainObj)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
    }

    @Override
    @CacheEvict(value = "Products", key = "#product.id")
    @Transactional
    public Product create(Product product) {
        try {
            com.stripe.model.Product stripeProduct = com.stripe.model.Product.create(ProductCreateParams.builder()
                    .setName(product.getName())
                    .setDescription(product.getDescription())
                    .setDefaultPriceData(ProductCreateParams.DefaultPriceData.builder()
                            .setCurrency("BRL")
                            .setUnitAmount(product.getPrice().multiply(new BigDecimal(100)).longValue()).build())
                    .build());

            product.setStripeId(stripeProduct.getId());

            ProductModel productModel = productRepository.save(ProductModel.from(product));

            return productModel.toDomainObj();
        } catch (StripeException e) {
            throw new TransactionFailedException("Erro ao salvar o produto no Stripe: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> search(String param, Integer page, Integer size) {
        return productRepository.search(param, PageRequest.of(page, size))
                .stream()
                .map(ProductModel::toDomainObj)
                .toList();
    }

    @Override
    @CacheEvict(value = "Products", key = "#product.id")
    @Transactional
    public Product update(Product product) {
        Product productToUpdate = findById(product.getId());

        try {
            com.stripe.model.Product stripeProduct = com.stripe.model.Product.retrieve(productToUpdate.getStripeId());

            if (product.getPrice().compareTo(productToUpdate.getPrice()) != 0) {
                String previusPriceId = stripeProduct.getDefaultPrice();

                PriceCreateParams nextPriceParams = PriceCreateParams.builder()
                        .setCurrency("BRL")
                        .setUnitAmount(product.getPrice().multiply(new BigDecimal(100)).longValue())
                        .setProduct(stripeProduct.getId())
                        .setActive(true)
                        .build();
                Price nextPrice = Price.create(nextPriceParams);

                ProductUpdateParams productUpdateParams = ProductUpdateParams.builder()
                        .setDefaultPrice(nextPrice.getId())
                        .build();
                stripeProduct.update(productUpdateParams);

                if (previusPriceId != null) {
                    Price previusPrice = Price.retrieve(previusPriceId);
                    PriceUpdateParams previusPriceParams = PriceUpdateParams.builder()
                            .setActive(false)
                            .build();
                    previusPrice.update(previusPriceParams);
                }
            }

            ProductUpdateParams productUpdateParams = ProductUpdateParams.builder()
                    .setName(product.getName())
                    .setDescription(product.getDescription())
                    .build();
            stripeProduct.update(productUpdateParams);
        } catch (StripeException e) {
            throw new TransactionFailedException("Erro ao atualizar o produto no Stripe: " + e.getMessage());
        }

        ProductModel productModel = ProductModel.from(productToUpdate);
        
        productModel.update(product);

        return productRepository.save(productModel).toDomainObj();
    }

}
