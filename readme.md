# Dead links checker

Check website for dead links. It supports recursive link search on the selected domain.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

1. Java 13.0.2
2. Jsoup 1.13.1

## Deployment

Unzip project folder and follow next steps:

Open InspectWebLinks.java

Update inspect function with with the site you want to check:

```
public static void main(String[] args) throws IOException {

  inspect("https://www.example.com");
  generate_report(reportData,"C:\\report.txt");

}
```

To start the program, run it from command prompt or Eclipse.

```
$ javac InspectWebLinks.java
$ java InspectWebLinks.java
```

Command prompt will output results and corresponding report.txt will be generated.

```
$ ...Finished! ( 0 empty) (22 skipped) ( 0 broken) ( 3 valid)
```

## Authors

* **Primož Flander** - *Initial work* - [primozflander](https://github.com/primozflander)
