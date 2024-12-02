# Investment Recommendation and Stock Data API

## Description
**Investment Recommendation and Stock Data API** is a Java-based application designed to fetch real-time stock information for specified assets. It retrieves and processes daily stock data, including opening price, high, low, and closing prices, and is being expanded to recommend investments based on fundamental analysis.

## Table of Contents

- [Description](#description)
- [How to Use](#how-to-use)
- [Features](#features)
- [Dependencies](#dependencies)
- [Execution](#execution)

## How to Use

### 1. Clone the Repository
Clone the repository to your local machine:
```bash
git clone <repository-link>
```

### 2. Open in an IDE
Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse). Ensure Maven is configured to manage dependencies.

### 3. Configure Stock List
Update the stock symbols in the `vetor` array inside the `GetInfos` class to include the desired assets. Example:
```java
String[] vetor = {"AAPL", "GOOG", "MSFT"};
```

### 4. Run the Application
Run the `GetInfos` class as a Java application. The application fetches stock data for the specified assets and logs the output to the console.

## Features

- **Real-Time Stock Data Retrieval**: Fetches daily stock data (open, high, low, close) using Yahoo Finance API.
- **Data Processing**: Identifies daily highs and lows for each stock.
- **JSON Parsing**: Handles complex JSON structures for accurate data extraction.
- **Extensible Design**: Structured to integrate future features like investment recommendations.

## Dependencies

The project requires the following dependencies:

- `org.json` (for JSON parsing)
- `jopendocument` (for working with spreadsheets)
- `java.logging` (for logging errors and information)

Ensure these dependencies are included in your project configuration.

## Execution

### From an IDE

1. Open the `GetInfos` class.
2. Run it as a Java application.

### From the Command Line

1. Compile the application:
   ```bash
   javac -cp "path/to/dependencies/*" Main/GetInfos.java
   ```

2. Run the compiled program:
   ```bash
   java -cp "path/to/dependencies/*:." Main.GetInfos
   ```
