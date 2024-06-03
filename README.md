# UnitTesting-MVVM-Kotlin-Koin-Coroutines-Sample

This project uses currency converter apis to demostrate the unit testing in Android app using MVVM,Kotlin,Coroutines and Koin.

***MVVM*** stands for Model, View, ViewModel.

Model: This holds the data of the application. It cannot directly talk to the View. Generally, it’s recommended to expose the data to the ViewModel through Observables.
View: It represents the UI of the application devoid of any Application Logic. It observes the ViewModel.
ViewModel: It acts as a link between the Model and the View. It’s responsible for transforming the data from the Model. It provides data streams to the View. It also uses hooks or callbacks to update the View. It’ll ask for the data from the Model.

***A Coroutine*** is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously. Coroutines were added to Kotlin in version 1.3 and are based on established concepts from other languages.

On Android, coroutines help to manage long-running tasks that might otherwise block the main thread and cause your app to become unresponsive. This topic describes how you can use Kotlin coroutines to address these problems, enabling you to write cleaner and more concise app code.

***Koin*** is a lightweight dependency injection framework, that uses Kotlin’s DSLs to lazily resolve your dependency graph at runtime.


Unit Testing Frameworks used -<br/>
Junit<br/>
Mockito<br/>
Okhttp Web server mock<br/>

Learn about usage of <a href="https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2">mock-maker-inline</a>


**Output**


![GifDemo](https://github.com/rishabhtanwar/Unit-Test-Koin/blob/master/WhatsAppVideo2024-06-03at7.07.12PM-ezgif.com-video-to-gif-converter.gif)

Happy Coding!!
