# 5 BDD & Gherkin Syntax

Since Python is used as a backend language for our project, all BDD tests are implemented using the behave framework.

## 5.1 Feature Files
We took three of our use cases defined in [use-cases](use-cases), this being
- [use-cases/uc-003.md](use-cases/uc-003.md) View task list
- [use-cases/uc-004.md](use-cases/uc-004.md) Create task
- [use-cases/uc-006.md](use-cases/uc-006.md) Notify user

and defined scenarios for them in Gherkin syntax (Given/When/Then).

The correspondent `*.feature` files can be found in the directory [backend/features](../backend/features), each one of those files contains Gherkin scenarios based on the respective use case.

## 5.2 Step Files

The corresponding step definitions for the BDD tests are located under [backend/features/steps](../backend/features/steps) in `*_steps.py` files. They contain the Python implementation for the Gherkin steps.

## 5.3 Test automation

To simplify the execution of the BDD tests, a [behave.ini](../behave.ini) file has been created. One does not need to provide the path(s) for the file(s) mentioned above, a simple `behave` command from the CLI executes the tests from the defined directory.

In our opinion, it does not make sense to execute BDD tests together with unit tests at each run, because they represent another layer of testing. Unit tests verify individual functions, whereas BDD tests verify the behaviour of a system.

Therefore it does make sense to execute unit tests at every commit or push. BDD tests can or should be run less frequently, e.g. when merging branches or at nightly builds.