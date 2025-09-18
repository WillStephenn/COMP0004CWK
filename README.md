# Java Servlet Notes Application

A web-based notes application built for the COMP0004 coursework. This project uses a Model-View-Controller (MVC) architecture with Java Servlets and JSPs to provide a dynamic note-taking experience.



---

## Application Features

The application provides a comprehensive set of features for managing personal notes. It supports full CRUD (Create, Read, Update, Delete) functionality, which is handled by a suite of dedicated servlets. Users can create and update notes through an `EditNoteServlet`, view them with `ViewNoteServlet`, and remove them using a `DeleteNoteServlet` that includes a confirmation prompt. All modifications are automatically saved to a JSON file, eliminating the need for manual saving.

The system is designed to handle various content types, including text-based notes, URL references, and image paths. For organisation, notes can be assigned to multiple categories, allowing users to filter the main index by topic. Additional organisational tools include options to sort notes by their creation date, last modified date, or alphabetically by title. The application also features a search function that finds notes based on keywords in both titles and content, with results highlighting the matching text. Finally, users can switch between a summary view that shows basic information and a full view that displays the complete note content.

---

## Architecture and Design

The application is built upon the **Model-View-Controller (MVC)** design pattern to ensure a clean separation of concerns.



* **Model**: The model layer handles all data operations and storage in JSON format. It uses a `Note` class to represent individual notes and maintains them in memory within a `HashMap` for efficient O(1) access operations. A `ModelFactory` implementing the Singleton pattern ensures that a single instance of the model is used throughout the application, preventing potential data inconsistencies. Data persistence is achieved by serialising a list of notes to a JSON file whenever a modification occurs.

* **View**: The user interface is rendered using JavaServer Pages (JSPs). Views exist for the main note index, viewing a single note, and an editor page for creating and updating notes.

* **Controller**: Java Servlets act as the controllers, processing incoming requests and interacting with the model.

---

## Getting Started

To run this project locally, you will need a Java EE application server like Apache Tomcat.

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/servlet-notes-app.git](https://github.com/your-username/servlet-notes-app.git)
    ```
2.  **Build the project** to produce a `.war` file.
3.  **Deploy the `.war` file** to your Apache Tomcat `webapps` directory.
4.  **Start the Tomcat server** and navigate to the application URL (e.g., `http://localhost:8080/servlet-notes-app`).

---

## Critical Evaluation and Future Work

The project successfully implements all core requirements while maintaining good design principles. Its key strengths lie in its proper Object-Oriented design, which demonstrates a clear separation of concerns, encapsulation, and cohesive classes. The application features a flexible note structure that supports multiple content types and a functional user interface.

While the JSON-based persistence system effectively meets the project's requirements, there are several areas for future improvement. A primary enhancement would be to implement a proper image upload function, as the current system only accepts file paths to images already within the web application directory. Other potential improvements include adding input validation and text formatting capabilities. Additionally, the application's styling could be better centralised in a single `styles.css` file rather than being scattered across multiple JSP files, which would improve the separation of concerns.

---

## Acknowledgements

For testing purposes, sample note text data was synthesised using an LLM (Claude 3.7 Sonnet), and an image was generated using the experimental version of Gemini Flash 2.0.
