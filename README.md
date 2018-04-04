**
# InLibrary Manager

A course project - Library managing system.

Checkout the [Projects](https://github.com/lenargum/libraryProject/projects) tab for more info about current tasks.

Different parts are being developed in different branches.

## Desktop application

### Requirements
- Java 8
- JUnit 5.0 (for tests)
- SQLite JDBC ([Installation guide](http://telegra.ph/Kak-sdelat-tak-chtoby-vsyo-zarabotalo-03-01))
- JavaFX 8
- [JFoeniX 8.0.3](https://github.com/jfoenixadmin/JFoenix)

JavaFX 8 is required for desktop application. Please install FX module if you are using OpenJDK, or use **Oracle JDK instead (recommended)**.

**JUnit 5.0** is required for building and running tests.

## Building

This project uses Gradle build system.

To build project please run `build.sh` (`build.bat` for Windows).

The compiled application will be stored in `/app` folder.

**Important note:** To run build shell script on *nix systems please ensure that you allowed `build.sh` and `gradlew` to be executed.

## Using
To use application you need to authorize first.
Please use one of these credentials:
| Login     | Password  |
|--------   |-----------|
| `patron1` | `patpass` |
| `patron2` | `patpass` |
| `patron3` | `patpass` |
| `patron4` | `patpass` |
| `patron5` | `patpass` |
| `librarian`| `lib-pass`|
These users are being added to database after running tests.

## Team contribution
- [Lenar Gumerov](https://github.com/lenargum) – tools.Database routines and its API
- [Anastasia Minakova](https://github.com/stalem9) – Core, documents.Document entities
- [Madina Gafarova](https://github.com/gafmn) – Core, users.User entities
- [Ruslan Shakirov](https://github.com/Shakirovrrr) – Desktop app; Testing
