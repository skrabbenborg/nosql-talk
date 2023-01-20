# Database Specialisation
Welcome to my JCore specialisation. In this repository you can find the progress I've made on the various goals I've 
set.

# Database types

1. Relational
2. Non-relational
   * Document
   * Key-value
   * Graph
   * Column family

# Database choices

1. Relational 
   1. PostgresSQL 
   2. MariaDB 
   3. Oracle

2. Non-relational
   1. MongoDB
   2. Elasticsearch
   3. Redis
   4. GraphQL
   5. Cassandra

# Decision tree

```mermaid
graph TD
    Start --> Intro{Question A}
    Intro --> |Yes| SQL[Branch 1]
    Intro --> |No| NoSQL[Branch 2]
    
    SQL --> C1{Question B}
    C1 --> |Yes| D1[Leaf 1]
    C1 --> |No| D2[Leaf 2]

    NoSQL --> C2{Question C}
    C2 --> |Yes| D3[Leaf 1]
    C2 --> |No| D4[Leaf 2]
```

### Links
- [Mermaid editor](https://mermaid.live)

### Sources
- [Types of SQL databases](https://www.altexsoft.com/blog/business/comparing-database-management-systems-mysql-postgresql-mssql-server-mongodb-elasticsearch-and-others/)
- [Types of NoSQL databases](https://www.mongodb.com/scale/types-of-nosql-databases)
