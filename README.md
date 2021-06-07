# Delivery
This is a server side of product delivery application.

It contains API for admin panel and mobile application.

The application provides registration of several stores.

The process of authentication and authorization of administrator is implemented by Spring Security and JWT.

The store administrator is able to:
- create a list of product groups, products and modifiers;
- upload product images;
- define delivery areas, store opening hours;
- connect the bonus system;
- register store departments, couriers and cooks;
- change status of customer's order;
- receive statistics on orders for different periods.

Registration and authorization of customers is carried out via SMS messages.

The customer is able to:
- get information about the store;
- make an order;
- pay for the order;
- repeat the order;
- receive push notifications about order status changes.
