# Duke's Klein Compiler

Hello and welcome to Duke's Klein Compiler! This is a class project written for CS 4550: Translation of Programming Languages, provided
at the University of Northern Iowa. Our task as a team is to develop a compiler for a language developed by our professor, Dr.
Walllingford, called Klein. This compiler will take Klein code as input, and produce TM (developed by [Kenneth Louden](http://www.cs.sjsu.edu/faculty/louden/)) assembly as output. We chose Java as our implementing language, and an Object-Oriented design
principle.

## Known Issues
#### Scanner:
* Treats negative integers as if they were positive when doing range check.
#### Parser:
* AST is binding operator nodes in wrong order (e.g.  Klein grammar calls for the '/' to bind before the '*').

## Getting Started

To get started, clone the git repository by entering the following string to a command-line with git installed:
```
git clone https://github.com/coswald/Dukes.git
```
Once that's done, you are ready to look at, edit, and use the code!


### Prerequisites

You will need a Java Development kit, at least 1.7 or higher. We're not going to delve into how to install that, as you can use Google
to find that out yourself. You'll also need Git; again, we're not going to tell you how to install that either. In order to compile the
source, you will need a Unix system such as Mac or Linux, or a version of make compatible with windows. The tests scripts are written
for .sh executables, so they will not execute natively on Windows.

### Installing

Once you clone the git repository, you should be able to compile the source code. To do so, simply run the make command like such:
```
make
```
However, as of now, this will only create a `.jar` file and the `kleinc` program. In order to generate other Klein compiler tools, see
the section labeled `Running the tests`.

## Running the tests

All of the compiler tools are provided within the `compiler-tools` directory. However, they will not run in their current directory.
Luckily, running a special `make` command will move them to where you need them to be. Simply type:
```
make tools
```
This will move the `kleins`, `kleinf`, `kleinv`, and `kleinp` into the project directory.

### Break down into end to end tests

The first test is for the `KleinScanner` class. This test is found in `kleins.java`, as well as the `kleins` executable. This will
generate tokens for a simple Klein program. For this test, we created `print-one.kln` and copied code provided to us by Dr. Wallingford.
The second test is for the `KleinParser` class. This tests is found in `kleinf.java`, as well as the `kleinf` executable. This will
tests whether a klein file is a valid program. For this test, we created illegal programs such as `vector-dot-product.kln`.
The third test is also for the `KleinParser` class. This tests is found in `kleinf.java`, as well as the `kleinf` executable. This will
tests whether a klein file is a valid program. For this test, we created a legal program called `pi.kln`, which will generate the amount
of digits in pi specified.

## Deployment

Once you have run the make file, you are ready to go! Using the compiler tools, you should be able to make and test Klein programs.

## Built With

* [JDK 1.7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html) - At least 1.7

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the
process for submitting pull requests to us.
This code of conduct is borrowed without permission, and is subject to change by the current contributors.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/coswald/Dukes/tags). 

## Authors

* **Coved W Oswald** - *Git Hoster* - [coswald](https://github.com/coswald/)
* **Daniel Holland** - *Duke Captain* - [djolland](https://github.com/djolland/)
* **Patrick Sedlacek** - *The Awesome* - [dunkhelExploit](https://github.com/dunkhelExploit/)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project. Dukes UNITE!

## License

This project is licensed under the GNU GPL 3.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments

Thanks to Dr. Eugene Wallingford, for the help provided in making the project. Also a thanks to the professors at UNI who taught us how
to code in the first place.
