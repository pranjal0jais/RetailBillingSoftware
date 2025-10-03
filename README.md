# Retail Billing System

A comprehensive Spring Boot-based retail billing system with integrated payment processing, inventory management, and user authentication.

## Features

- **Authentication & Authorization**: JWT-based authentication.
- **Category Management**: Create, read, and delete product categories with image uploads
- **Item Management**: Full CRUD operations for retail items with category association
- **Order Management**: Create and manage customer orders with detailed item tracking
- **Payment Integration**: Integrated Razorpay payment gateway for UPI transactions
- **Dashboard Analytics**: Real-time sales metrics and order tracking
- **Cloud Storage**: Cloudinary integration for image management

## Technology Stack

- **Framework**: Spring Boot
- **Security**: Spring Security with JWT
- **Database**: MySQL (with JPA/Hibernate)
- **Cloud Storage**: Cloudinary
- **Payment Gateway**: Razorpay
- **Build Tool**: Maven/Gradle

## Prerequisites

- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+
- Cloudinary account
- Razorpay account

## Environment Variables

Create a `.env` file or set the following environment variables:

```properties
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/retail_billing
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# Cloudinary Configuration
CLOUDINARY_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret

# JWT Configuration
JWT_SECRET_KEY=your_jwt_secret_key

# Razorpay Configuration
RAZORPAY_KEY_ID=your_razorpay_key_id
RAZORPAY_KEY_SECRET=your_razorpay_key_secret
```

## Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd RetailBillingSystem
   ```

2. **Configure the database**
   ```sql
   CREATE DATABASE retail_billing;
   ```

3. **Set environment variables**
   - Update the environment variables as mentioned above

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/login` | User login | Public |
| POST | `/encode` | Password encoding utility | Public |

### Categories

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/categories` | Get all categories | Public |
| POST | `/admin/categories/add` | Add new category | ADMIN |
| DELETE | `/admin/categories/{categoryId}` | Delete category | ADMIN |

### Items

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/items` | Get all items | Public |
| POST | `/admin/items/add` | Add new item | ADMIN |
| DELETE | `/admin/items/{itemId}` | Delete item | ADMIN |

### Orders

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/admin/orders/create-order` | Create new order | ADMIN |
| DELETE | `/admin/orders/delete/{orderId}` | Delete order | ADMIN |
| GET | `/latest-orders` | Get latest orders | Public |

### Payments

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/payments/create-order` | Create Razorpay order | ADMIN |
| PATCH | `/payments/update-payment-info` | Update payment status | ADMIN |

### Dashboard

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/dashboard` | Get dashboard metrics | ADMIN |

## Request Examples

### Login Request
```json
POST /login
{
  "email": "admin@example.com",
  "password": "password123"
}
```

### Create Category Request
```
POST /admin/categories/add
Content-Type: multipart/form-data

category: {
  "name": "Electronics",
  "description": "Electronic items and gadgets"
}
file: [image file]
```

### Create Order Request
```json
POST /admin/orders/create-order
{
  "customerName": "John Doe",
  "phoneNumber": "1234567890",
  "itemTotal": 1000.0,
  "tax": 180.0,
  "grandTotal": 1180.0,
  "paymentMethod": "UPI",
  "cartItems": [
    {
      "itemId": "item-uuid",
      "itemName": "Product Name",
      "quantity": "2",
      "totalAmount": "1000.0"
    }
  ]
}
```

## Security Configuration

- CORS is enabled for `http://localhost:3000`
- JWT tokens expire after 1 hour
- All admin endpoints require ADMIN role
- Stateless session management

## Payment Flow

1. Create order with `PENDING` status
2. Generate Razorpay order via `/payments/create-order`
3. Process payment on frontend
4. Update payment info via `/payments/update-payment-info`
5. Order status updates to `COMPLETED` or `FAILED`

## Database Schema

### Key Entities

- **UserEntity**: User authentication and profile
- **CategoryEntity**: Product categories with images
- **ItemEntity**: Products with category association
- **OrderEntity**: Customer orders with payment details
- **OrderItemEntity**: Individual items in an order

## Project Structure

```
src/main/java/com/pranjal/
├── config/              # Security and application configuration
├── controller/          # REST API controllers
├── dto/                 # Data Transfer Objects
├── entity/              # JPA entities
├── filter/              # JWT authentication filter
├── JwtUtil/             # JWT utility classes
├── repository/          # JPA repositories
└── service/             # Business logic layer
    └── Impl/            # Service implementations
```

## Development Guidelines

- Use constructor injection for dependencies
- Follow RESTful API conventions
- Implement proper error handling
- Use DTOs for API requests/responses
- Keep business logic in service layer

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Support

For issues and questions, please create an issue in the repository.

## Contact

For questions, support, or feedback, please reach out:

- **Developer**: Pranjal Jais
- **Email**: pranjaljais2@gmail.com
- **Project Repository**: https://github.com/pranjal0jais/Home-Appliance-Manager/
- **Issues**: Report bugs or request features through GitHub Issues
- **LinkedIn**: https://www.linkedin.com/in/pranjal-jais-549433366/

For urgent matters or security concerns, please email directly with "URGENT" in the subject line.

**Note**: Ensure all sensitive credentials are kept secure and never committed to version control.
