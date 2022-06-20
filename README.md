# CryptoTracker

It was created with Mvvm design pattern and Clean arc approach.

- Mvvm desing pattern
- Clean Arc.
- Architecture Components
  * WorkManager
  * Room
  * Livedata
  * Navigation
- Reactivex Coroutines&Flow
- RestFull api Retrofit
- DI Dagger&Hilt
- TestMock Mockito
- UnitTestReport Jacoco
- LintCheck Ktlint 
 
 
# Screen shot
<img src="https://github.com/seyfullahpolat/CryptoTracker/blob/3435da539ac10908ec69abc7febbafd551bb0283/ss/Screenshot_20220607_232434.png" width=20% height=20%/>  <img src="https://github.com/seyfullahpolat/CryptoTracker/blob/3435da539ac10908ec69abc7febbafd551bb0283/ss/Screenshot_20220607_232509.png" width=20% height=20%/> <img src="https://github.com/seyfullahpolat/CryptoTracker/blob/3435da539ac10908ec69abc7febbafd551bb0283/ss/Screenshot_20220607_232534.png" width=20% height=20%/> <img src="https://github.com/seyfullahpolat/CryptoTracker/blob/3435da539ac10908ec69abc7febbafd551bb0283/ss/Screenshot_20220607_232647.png" width=20% height=20%/>
 
# Gradle Task
* ./gradlew unitTestReport (path: .../app/build/report/index.html)
* ./gradlew ktlintCheck
* ./gradlew ktlintFormat



# Background running logic
I used alarmmanager to make a request in the background. I saw that it does not work stable based on device. The problem was that it didn't deliver the alarms on time. I tried to handle this situation using two work managers.

* The first worker is the SetTimeUnlessFiveWorkManager class.
I would return Result.Retry to the SetTimeUnlessFiveWorkManager doWork method. I set setBackoffCriteria to linear. https://developer.android.com/topic/libraries/architecture/workmanager/how-to/define-work. I set the initial value to 1 minute.
It runs at 1,2,3... minute intervals.

* The second workmanager is the PeriodicTimeFifteenWorkManager class.
It resets the SetTimeUnlessFiveWorkManager worker each time this worker runs. In this way, every 15 seconds, five requests are made at intervals of 1,2,3,4,5 (15 total) minutes, respectively.
 
