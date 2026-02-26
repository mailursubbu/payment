# Transaction Recorder
This application is for recording the transactions. Its built using the simple Java Util Set\<Entity\> 
based data storage for storing Account and Transactions. Ids are generated using AtomicInteger based counter
for Account and Transaction entities. Please note as the DB is only in memory, all the updates would be 
lost on application restart.

# Build and Run

* docker build -t my-springboot-app .
* docker run -it -p 8080:8080 my-springboot-app


# APIs

1. Create Account
   * curl --request POST \
     --url http://localhost:8080/account \
     --header 'content-type: application/json' \
     --data '{
     "document_number" : "Shrihari Kapadu"
     }'
   * Output
     * {"account_id":2,"document_number":"Shrihari Kapadu"}
2. Read Account
   * curl --request GET \
     --url http://localhost:8080/account/1
   * Output
     * {"account_id":1,"document_number":"Shrihari Kapadu"}
3. Post Transaction
   * curl --request POST \
     --url http://localhost:8080/transactions \
     --header 'content-type: application/json' \
     --data '{
     "account_id": 1,
     "operation_type_id": 4,
     "amount": 123.46
     }
     '
   * Output
     * {"transaction_id":2,"account_id":1,"operation_type_id":4,"amount":123.46,"eventDate":"2026-02-26T17:48:23.048400797"}
3. Get Transaction by Id
   * curl --request GET \
     --url http://localhost:8080/transactions/1
   * Output
     * {"transaction_id":2,"account_id":1,"operation_type_id":4,"amount":123.46,"eventDate":"2026-02-26T17:48:23.048400797"}


