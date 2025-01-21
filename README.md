<h1 align="center" style="font-weight: bold;">Emporium 🛒</h1>

<p align="center">
  <a href="#inicio">In English</a> •
  <a href="README.pt-br.md">Em português</a>
</p>

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
 <a href="#documentation">Access documentation</a> •
 <a href="#run">Run project</a> •
 <a href="#how-to-use">How to use</a> • 
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>Emporium is a REST API for an E-commerce that uses Stripe as a payment gateway and allows creating resources such as products and discount coupons. Additionally, the project was developed following the principles of <a href=https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c>Clean Architecture</a> and with the best and most up-to-date practices to ensure the integrity of sensitive data.</b>
</p>

<h2 id="documentation">📄 Access documentation</h2>

The project is hosted on a cloud service. Therefore, you can access its [documentation](https://emporium-production.up.railway.app/api/v1/swagger-ui/index.html#/) developed through Swagger UI without the need to run it locally.

<h2 id="run">⚙️ Run project</h2>

<h3>Prerequisites</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)
- [Stripe Account](https://stripe.com/)

<h3>Cloning</h3>

```bash
git clone https://github.com/gabrieudev/emporium.git
```

<h3>Environment Variables</h3>

To run the application, you will need to set two environment variables related to the Stripe service: [Secret Key](https://dashboard.stripe.com/test/apikeys) and [Stripe Webhook Secret](https://docs.stripe.com/webhooks).

Note: The event type chosen for creating the Stripe webhook secret should be `checkout.session.completed`.

<h3>Starting</h3>

Run the following commands, replacing the two variables:

```bash
cd emporium
STRIPE_KEY=<secret_key> STRIPE_WEBHOOK_SECRET=<webhook_secret> PROFILE=dev docker compose up -d
```

<h2>🔁 How to use</h2>

1. Create a user with `POST /users/signup`

2. Log in with `POST /auth/signin` and copy the `accessToken` value.

3. Go to `Authorize` in the top field of the Swagger interface and enter the copied value. This way, all requests will automatically include the access token.

4. Get your cart with `GET /carts`, search for a product with `GET /products`, and then create a new cart item with `POST /cart-items`.

5. Get your cart again with `GET /carts`, now updated, and create a new order with `POST /orders`.

6. Optionally, you can add a discount coupon to your order. Just create a discount with `POST /discounts` using the following test coupon with a 10% discount:

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
  "updatedAt": "2025-01-20T18:54:52.932639325"
}
```

If you didn't copy your order, you can retrieve it with `GET /orders` by inserting your user ID. If you don't know your user ID, you can get it with `GET /users/me`.

Note: The coupon will only be usable for orders with a minimum value of 100 and until 01/20/2027.

7. Obtain the payment link for your order with `GET /orders/{UUID}/payment-link` and access it.

8. Fill in the required personal fields (it is recommended to use fictitious information). For the payment method, enter the following test card:

```yaml
Card number: 4242 4242 4242 4242
Expiration date: 01/30
Security code: 420
```

Finally, you will be redirected back to the Swagger interface. Now, your cart will be empty, and your order will be updated with the information you entered during payment.

<h2 id="contribute">📫 Contribute</h2>

Contributions are very welcome! If you want to contribute, fork the repository and create a pull request.

1. `git clone https://github.com/gabrieudev/emporium.git`
2. `git checkout -b feature/NAME`
3. Follow the commit standards.
4. Open a Pull Request explaining the issue solved or the functionality developed. If there are visual changes, attach screenshots of the modifications and wait for the review!

<h3>Helpful documentation</h3>

[📝 How to create a Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)

[💾 Commit standard](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
