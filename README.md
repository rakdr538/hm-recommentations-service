Docker is needed in the environment to run both tests and the application.
init.sql is provided in src/test/resources to run test (has be be moved to src/main/resources before running app).
all necessary DB config is added to application.properties, just uncomment and configure accordingly.

Ref: Database Start

Assumptions:

Only logged-in or registered users, who have their shopping history with us.
Payment entities can be extended.
Shopping cart is not considered.
Delivery entities can be extended.
Promotions can be extended with via membership or via specific sub-category

Product_type: clothes or shoes or jewellery or home or beauty

Product_category: clothes -> t-shirt or jeans or pants or skirts

Disadvantages: filtering for size or colour is available.


