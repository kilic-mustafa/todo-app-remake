# 📝 ToDo App Remake

A backend-focused ToDo list application built with Spring Boot, MySQL, and Mockito. This project demonstrates CRUD operations, RESTful API design, and unit testing practices.

## 🚀 Features

- ✅ Create, Read, Update, and Delete (CRUD) tasks
- 📦 RESTful API endpoints for task management
- 🧪 Unit testing with Mockito
- 🗃️ MySQL database integration
- 🧩 Modular project structure for scalability

## 🛠️ Technologies Used

- Java
- Spring Boot
- MySQL
- Mockito

## 📦 Getting Started

1. **Clone the repository:**

   ```bash
   git clone https://github.com/kilic-mustafa/todo-app-remake.git
   cd todo-app-remake

2. **Set up the MySQL database:-**

- Create a new MySQL database.

- Execute the SQL script provided in todo-sql.txt to set up the necessary tables.

3. **Configure application properties:**

- Navigate to src/main/resources/application.properties

- Update the database connection settings:

   ```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
  spring.datasource.username=your_username
  spring.datasource.password=your_password
   
4. **Build and run the application:**

- mvn clean install
- mvn spring-boot:run
  
5. **Access the API:**

- The application will be running at http://localhost:8080/
- Use tools like Postman or curl to interact with the API endpoints.

## 🤝 Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.
