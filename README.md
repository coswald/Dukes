# Duke's Klein Compiler

Hello and welcome to Duke's Klein Compiler! This is a class project written for CS 4550: Translation of Programming Languages, provided
at the University of Northern Iowa. Our task as a team is to develop a compiler for a language developed by our professor, Dr. Walllingford,
called Klein. This compiler will take Klein code as input, and produce TM (developed by [Kenneth Louden](http://www.cs.sjsu.edu/faculty/louden/))
assembly as output. We chose Java as our implementing language, and an Object-Oriented design principle.

## Getting Started

To get started, clone the git repository by entering the following string to a command-line with git installed:
```
git clone https://github.com/coswald/Dukes.git
```
Once that's done, you are ready to look at, edit, and use the code!


### Prerequisites

You will need a Java Development kit, at least 1.7 or higher. We're not going to delve into how to install that, as you can use Google to
find that out yourself. You'll also need Git; again, we're not going to tell you how to install that.

### Installing

Once you clone the git repository, you should be able to compile the source code. To do so, create a file that contains all of the `.java`
paths within it, and we'll call this file `sources`. This is done on Windows by typing in the command prompt:

```
dir *.java /s/b > sources
```

This is done on Linux/Mac by typing in the Terminal:

```
find . -iname *.java > sources
```
**Note: this hasn't been tested on a Mac**

Once this is done, you should be able to compile the java sources by creating a directory called bin and placing the .class files there.
This is done as such:

```
mkdir bin
javac @sources -d bin
```

After this step, you should have a running version of the code. You can generate a .jar for compiler tools, but this should be done
for you within the `compiler-tools` directory. Success will be acheived if you can run the following command:

```
cd bin
java com.dukes.tests.KleinTest
```

## Running the tests

All of the compiler tools are provided within the `compiler-tools` directory. However, you can run incremental tests on certain aspects
of the compiler by running the `KleinTest` program found in the `com\dukes\tests` executable path.

### Break down into end to end tests

Currently, the only test is for the `KleinScanner` class. This test is found in `KleinScannerTest.java` This will generate tokens for
a simple Klein program.

## Deployment

Once you have the compiled compiler, you can generate a jar ***TODO*** EXPLAIN HOW TO GENERATE A JAR FOR COMPILER

## Built With

* [JDK 1.7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) - At least 1.7

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.
This code of conduct is borrowed without permission, and is subject to change by the current contributors.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/coswald/Dukes/tags). 

## Authors

* **Coved W Oswald** - *Git Hoster* - [coswald](https://github.com/coswald/)
* **Daniel Holland** - *Duke Captain* - [djholland](https://github.com/djholland/)
* **Patrick Sedlacek** - *The Awesome* - [dunkhelExploit](https://github.com/dunkhelExploit/)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project. Dukes UNITE!

## License

This project is licensed under the GNU GPL 3.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Thanks to Dr. Eugene Wallingford, for the help provided in making the project. Also a thanks to the professors at UNI who taught us how
to code in the first place.
