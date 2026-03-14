# Getting Started

## Prerequisites

- Java: OpenJDK 21 (GraalVM 21.0.0 or later recommended)
- Build Tool: Use Gradle Wrapper (`./gradlew`) included in this repository

## 1. Clone the repository

```bash
git clone https://github.com/Rubyrubylai/php-type-resolver.git
cd php-type-resolver
```

## 2. Build the project

Run from the repository root:

```bash
./gradlew clean build
```

## 3. Run tests

```bash
./gradlew test
```

This executes all unit tests and prints results in the console.

## Note on “running” this project

This project is configured as a Java library, not a runnable application.
So the primary way to use/verify it is through build and test tasks.

# Project Structure

```
app/src/main/
└── java/org/example/
    ├── resolver/              # Core resolver logic
    ├── parser/                # Parsing logic for @var tag value
    │   └── impl/
    ├── type/                  # PhpType abstraction, implementations, and factory logic
    │   └── impl/
    └── model/                 # Domain models representing PHP constructs
        └── impl/
```

- Separate interfaces from implementations to improve modularity and support the Dependency Inversion Principle.

# Design Descision

## 1. Adding `getName()` to `DocTag`

- Decision: I added a `getName()` method to the `DocTag` interface in addition to `getValue()`.
- Reasoning: Originally, the provided API only exposed the tag value. However, `PhpDocBlock.getTagsByName()` implies that tags must be filtered by their tag name. To support this cleanly, the tag name should be treated as part of the core data of a `DocTag`, rather than as an implementation-specific detail.
For this reason, I chose to store and expose the tag name in the `DocTag` interface instead of storing it separately inside `PhpDocBlock` using an auxiliary structure such as a map. The tag name is more naturally associated with `DocTag` itself. `PhpDocBlock` does not need to own this information directly; it only needs to call `DocTag.getName()` and filter the tags accordingly.
This also allows more flexible implementations of `DocTag` in the future, because the tag name is a fundamental aspect of what a tag is, rather than an incidental detail of how tags are stored inside a block.

## 2. Additional tests for malformed input

- Decision: I added extra test cases for edge cases and malformed PHPDoc input. For invalid, ambiguous, or malformed `@var` tags, the resolver returns mixed instead of throwing an exception.
- Reasoning: In addition to the expected behaviors described in the problem statement, I wanted to cover inputs that may occur in practice but do not follow the expected format. Because documentation is written manually, it cannot be assumed to always be valid or consistent. For this reason, malformed PHPDoc should be handled conservatively. Since this component is a resolver rather than a strict validator, it is better to degrade gracefully and return mixed when the type cannot be determined safely. This keeps the resolver tolerant of imperfect input and allows library users to decide how unresolved or malformed cases should be handled at a higher level.
