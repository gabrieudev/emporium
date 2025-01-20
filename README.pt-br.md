<h1 align="center" style="font-weight: bold;">Emporium 🛒</h1>

<p align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white" alt="Spring">
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens" alt="JWT">
  <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="Postgres">
  <img src="https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white" alt="Redis">
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker">
  <img src="https://img.shields.io/badge/Stripe-5469d4?style=for-the-badge&logo=stripe&logoColor=ffffff" alt="Stripe">
  <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white" alt="Swagger">
</p>

<p align="center">
 <a href="#documentacao">Acessar documentação</a> •
 <a href="#executar">Executar projeto</a> •
 <a href="#fluxo">Como usar</a> • 
 <a href="#contribuir">Contribuir</a>
</p>

<p align="center">
  <b>Emporium é uma API REST de um E-commerce que utiliza Stripe como gateway de pagamentos e criação de recursos como produtos e cupons de desconto. Além disso, o projeto foi desenvolvido seguindo os princípios da <a href=https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c>Arquitetura Limpa</a> e com as melhores e mais atualizadas práticas do mercado para assegurar a integridade dos dados sensíveis.</b>
</p>

<h2 id="documentacao">📄 Acessar documentação</h2>

O projeto está hospedado em um serviço de nuvem. Portanto, você poderá acessar sua [documentação](https://emporium-production.up.railway.app/api/v1/swagger-ui/index.html#/), desenvolvida através da Swagger UI, sem precisar executá-lo localmente.

<h2 id="executar">⚙️ Executar projeto</h2>

<h3>Pré-requisitos</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)
- [Conta Stripe](https://stripe.com/)

<h3>Clonando</h3>

```bash
git clone https://github.com/gabrieudev/emporium.git
```

<h3>Variáveis de Ambiente</h3>

Para executar a aplicação, você precisará definir duas varáveis de ambiente relacionadas ao serviço do Stripe: [Chave Secreta](https://dashboard.stripe.com/test/apikeys) e o [Segredo de webhook Stripe](https://docs.stripe.com/webhooks).

Observação: O tipo de evento escolhido para a criação do segredo de webhook Stripe deve ser o `checkout.session.completed`

<h3>Inicializando</h3>

Execute os seguintes comandos inserindo as duas variáveis:

```bash
cd emporium
STRIPE_KEY=<secret_key> STRIPE_WEBHOOK_SECRET<webhook_secret> PROFILE=dev docker compose up -d
```

<h2>🔁 Como usar</h2>

1. Crie um usuário em `POST /users/signup`

2. Faça login em `POST /auth/signin` e copie o valor de `accessToken`

3. Vá até `Authorize` no campo superior da interface Swagger e insira o valor copiado. Desta forma, todas as requisições terão o token de acesso automaticamente embutido

4. Obtenha seu carrinho em `GET /carts`, busque por algum produto em `GET /products` e, após isso, crie um novo item de carrinho em `POST /cart-items`

5. Obtenha seu carrinho novamente em `GET /carts`, agora atualizado, e crie um novo pedido em `POST /orders`

6. Opcionalmente, você pode adicionar um cupom de desconto ao seu pedido. Basta criar um desconto em `POST /discounts` usando o seguinte cupom de teste com 10% de desconto:

```bash
{
  "id": "d8ccd30a-8cca-4e09-bdfb-8729c117c033",
  "code": "ABC123",
  "percentOff": 10,
  "minOrderValue": 100,
  "validFrom": "2025-01-20T18:55:12.199",
  "validUntil": "2027-01-20T18:52:12.199",
  "usageLimit": 100,
  "usageCount": 0,
  "stripeId": "wrS5mdCc",
  "createdAt": "2025-01-20T18:54:52.932574701",
  "updatedAt": "2025-01-20T18:54:52.932639325",
}
```

Caso não tenha copiado seu pedido, você poderá buscá-lo em `GET /orders` inserindo o ID do seu usuário. Caso não saiba o ID do seu usuário, você pode obtê-lo em `GET /users/me`

Observação: O cupom somente será utilizável em pedidos com valor mínimo de 100 e até a data 20/01/2027

7. Obtenha o link de pagamento do seu pedido em `GET /orders/{UUID}/payment-link` e acesse-o.

8. Preencha os campos pessoais obrigatórios (recomendado usar informações fictícias). Quanto ao método de pagamento, insira o seguinte cartão de teste:

```yaml
Número do cartão: 4242 4242 4242 4242
Data de validade: 01/30
Código de segurança: 420
```

Por fim, você será redirecionado novamente para a interface Swagger. Agora, seu carrinho estará zerado e o seu pedido estará atualizado com as informações que foram preenchidas no pagamento.

<h2 id="contribuir">📫 Contribuir</h2>

Contribuições são muito bem vindas! Caso queira contribuir, faça um fork do repositório e crie um pull request.

1. `git clone https://github.com/gabrieudev/emporium.git`
2. `git checkout -b feature/NOME`
3. Siga os padrões de commits.
4. Abra um Pull Request explicando o problema resolvido ou a funcionalidade desenvolvida. Se houver, anexe screenshots das modificações visuais e aguarde a revisão!

<h3>Documentações que podem ajudar</h3>

[📝 Como criar um Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Padrão de commits](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
