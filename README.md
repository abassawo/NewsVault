# News Vauilt

### Unit Tests
Unit tests exist under the "test" directory. They can be run using the standard commands. ex. `./gradlew testDebugUnitTest`

### UI/Instrumentation Tests
UI (Espresso) tests exist under the "androidTest" directory. The project also uses [Spoon](https://github.com/square/spoon) and its [gradle plugin](https://github.com/stanfy/spoon-gradle-plugin) to run instrumentation tests and generate a easy to read report. To run an instrumentation test, use `./gradlew spoon`.

### Code Coverage
Code coverage configuration are handled by [coverage.gradle](app/coverage.gradle). To generate a code coverage report, use `./gradlew testCoverage`. This will run both unit and instrumentation tests and merge the result of both tests into a single report.

## Architecture
### Model-View-Presenter
The app uses the popular MVP architecture to allow for separation of logic and ease of testing. In this paradigm, all business logic should live inside presenters (but they can delegate some tasks to other classes that are injected as dependencies). Activities and fragment will act as "views", they should not have any logic other than passing the user events to the presenter and displaying the data. There are also Contract classes that specify the communication interface between the views and presenters.  

### Base Classes
- `BaseActivity`: Base class for all activities. Includes lifecycle logging and view inflation.
- `BaseMvpActivity`: Base class for activities that will have some business logic instead of just hosting a fragment. Includes setup for interactions with presenter.
- `BaseFragmentActivity`: Base class for activities whose sole purpose to to host a fragment. If the activity contains any additional logic, use `BaseMvpActivity` instead.
- `BaseFragment`: Basically the same as `BaseMvpActivity`, but for fragments.
- `BasePresenter`: Base class for all presenters. Includes lifecycle setup and common dependencies. Goes along with `BaseMvpActivity` and `BaseFragment`.
- `BaseContract`: Includes interfaces that all views and presenters should implement.
- `PresenterConfiguration`: Wrapper class for common dependencies that all presenters are expected to have.

### Third Party Libraries
- Retrolambda (adds support for lambda expressions in Java 6)
- RxJava/RxAndroid (helps with asynchronous event handling)
- Retrofit/OkHttp (simplifies network requests)
- Timber (better logging tool)
- Picasso (simplifies image loading)
- LeakCanary (helps catch memory leaks)
- Crashlytics (reports crashes)
- Mockito (mocks out classes for tests)
- Espresso (UI testing framework)
- Spoon (displays the test results in an easy to read format)

![News Vault Customize](https://media.giphy.com/media/AFuMIzsD6tO5CtNiBN/giphy.gif)
![News Vault Share](https://media.giphy.com/media/5eF6HwmvfeQ4ySXjhB/giphy.gif)
![News Vault Night Mode](https://media.giphy.com/media/j0NHRwhZYBLnjOPFFa/giphy.gif)
