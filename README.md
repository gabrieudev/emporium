<h1 align="center" style="font-weight: bold;">Emporium 🛒</h1>

<p align="center">
  <a href="README.pt-br.md">Português (Brasil)</a> •
  <a href="#start">English</a>
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
 <a href="#documentation">Access Documentation</a> •
 <a href="#run">Run Project</a> •
 <a href="#how-to-use">How to Use</a> • 
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>Emporium is a REST API for an E-commerce platform using Stripe as a payment gateway and managing resources like products and discount coupons. The project follows the principles of <a href="https://medium.com/@gabrielfernandeslemos/clean-architecture-uma-abordagem-baseada-em-princ%C3%ADpios-bf9866da1f9c">Clean Architecture</a> and incorporates up-to-date industry best practices to ensure sensitive data integrity.</b>
</p>

<h2 id="documentation">📄 Access Documentation</h2>

The project is hosted on a cloud service. You can access its [documentation](https://emporium-production.up.railway.app/api/v1/swagger-ui/index.html#/), built with Swagger UI, without running it locally.

<h2 id="run">⚙️ Run Project</h2>

<h3>Prerequisites</h3>

- [Docker](https://www.docker.com/get-started/)
- [Git](https://git-scm.com/downloads)
- [Stripe Account](https://stripe.com/)

<h3>Cloning</h3>

```bash
git clone https://github.com/gabrieudev/emporium.git
```

<h3>Environment Variables</h3>

Create a `.env` file with the following Stripe-related environment variables: [Secret Key](https://dashboard.stripe.com/test/apikeys) and [Stripe Webhook Secret](https://docs.stripe.com/webhooks):

```bash
STRIPE_KEY=<secret_key>
STRIPE_WEBHOOK_SECRET=<webhook_secret>
```

> The Stripe webhook secret must be configured for the `checkout.session.completed` event type.

<h3>Initializing</h3>

Run the following commands:

```bash
cd emporium
docker compose up -d --build
```

<h2 id="how-to-use">🔁 How to Use</h2>

1. Create a user via `POST /users/signup` if running locally. Otherwise, use the admin credentials:

```yaml
email: "admin@gmail.com"
password: "adminpassword"
```

2. Log in via `POST /auth/signin` and copy the `accessToken` value.

3. Click `Authorize` at the top of the Swagger UI and paste the token to authenticate all requests automatically.

4. Fetch your cart via `GET /carts`, search for products via `GET /products`, then add items to your cart via `POST /cart-items`.

5. Fetch your updated cart via `GET /carts` and create an order via `POST /orders`.

6. Optionally, apply a discount coupon to your order. Create a test coupon with 10% off via `POST /discounts`:

```json
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

> This coupon is valid for orders over 100 units until 01/20/2027.

> If logged in as admin, create your own coupon instead of using the test example.

> Retrieve your order ID via `GET /orders` (use your user ID from `GET /users/me` if needed).

7. Get your payment link via `GET /orders/{UUID}/payment-link` and visit it.

8. Fill in required personal details (use fictional data). For payment, use the test card:

```yaml
Card Number: 4242 4242 4242 4242
Expiration: 01/30
CVC: 420
```

After payment, you'll be redirected back. Your cart will be empty, and the order will reflect the payment details.

<h2 id="contribute">📫 Contribute</h2>

Contributions are welcome! Fork the repository and submit a pull request.

1. `git clone https://github.com/gabrieudev/emporium.git`
2. `git checkout -b feature/NAME`
3. Follow commit conventions.
4. Open a Pull Request explaining changes. Include screenshots if applicable.

<h3>Helpful Documentation</h3>

[📝 How to Create a Pull Request](https://www.atlassian.com/git/tutorials/making-a-pull-request)

[💾 Commit Message Guidelines](https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716)
