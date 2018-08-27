# TimeTrackerTextView
> A (Time-Dependent) TextView that tracks time and updates its content accordingly.

![Platform](https://img.shields.io/badge/platform-Androd-green.svg)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=17)
[![Download](https://api.bintray.com/packages/mohaka/maven/time-tracker-text-view/images/download.svg) ](https://bintray.com/mohaka/maven/time-tracker-text-view/_latestVersion)
[![GitHub issues](https://img.shields.io/github/issues-raw/mohakapt/TimeTrackerTextView.svg)](https://github.com/mohakapt/TimeTrackerTextView/issues)
[![License](https://img.shields.io/github/license/mohakapt/TimeTrackerTextView.svg)](https://github.com/mohakapt/TimeTrackerTextView)

I find it rather frustrating to implement a time-dependent TextView (A TextView that updates its properties every x period). You need to setup the timer at the right moment and manage its lifecycle manually and kill it when it finishes its work before it starts leaking memory, and even if you manage to do all that work right, that much code still messes your activity up.
<br/><br/>

![Example]()


### Installation
Add This dependency to your module-level gradle file:
```groovy
dependencies {
    implementation 'com.github.mohaka:time-tracker-text-view:0.1.0'
    
    // Add this dependency only if you want to use my PrettyTimeTracker
    // implementation 'org.ocpsoft.prettytime:prettytime:4.0.1.Final'
}
```


### Usage
* Please check the example in the source code for more detailed information.

## Roadmap


## Contributing
If you encounter a bug or you have a feature in mind please make a pull request and i will merge it as soon as possible, if you can't (for some reason) make a pull request please open an issue and i will happily do respond to it.


## Versioning
I use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/mohakapt/TimeTrackerTextView/tags).<br/>
I will try to provide release notes with future releases.


## License
This project is licensed under the Apache-2.0 license - see the [LICENSE.md](https://github.com/mohakapt/TimeTrackerTextView/blob/master/LICENSE) file for more details.
