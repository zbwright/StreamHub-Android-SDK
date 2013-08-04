StreamHub-Android-SDK
=====================

Make Android apps powered by Livefyre StreamHub

Read the docs: http://livefyre.github.com/StreamHub-Android-SDK/

# Getting Started

The Gradle build needs to be configured with the location of the Android SDK.
Create a file called `local.properties` and point to your install as follows:

    sdk.dir = /your_android_sdk_path

Depending on your development environment, build the library as follows:

#### Gradle/Android Studio

Run `./gradlew install` and add the following to your build.gradle:

    dependencies {
        compile 'com.livefyre:StreamHub-Android-SDK:0.0.1'
    }

#### Other environments

Run `gradle build` and include the built .aar file in `build/libs/`

# Running the examples

Each example has its own Gradle build and depends on the core library being installed. Run `gradle install` to install the library in the local Maven repo if you haven't done so already.

The examples also have hard-coded values for the article and user token values. Replace the hard-coded values with values appropriate to your application.

To build an example project, run `gradle build` in that project's directory (e.g. examples/commentstream).

The project can be run through Android Studio or from the command-line as follows (using the commentstream example):

    /<PATH TO SDK INSTALL>/sdk/platform-tools/adb install build/apk/commentstream-debug-unaligned.apk

# Building an application

## Environment Configuration
By default, the library uses the Livefyre production environment. To configure it for the UAT environment, add `Config.environment = "t402.livefyre.com";` to your application instantiation.

## Authentication
The first step in building an application will be to authenticate a user. Information (including a sequence diagram) on how to construct an LFToken can be found [`here`](https://github.com/Livefyre/livefyre-docs/wiki/Livefyre-authentication-token).

## Retrieving Collections and New Data
The [`BootstrapClient`](https://github.com/Livefyre/StreamHub-Android-SDK/blob/gradle/src/main/java/com/livefyre/android/core/BootstrapClient.java) is used to retrieve initial data for a collection.
To stream new data, use the [`StreamClient`](https://github.com/Livefyre/StreamHub-Android-SDK/blob/gradle/src/main/java/com/livefyre/android/core/StreamClient.java). Implementations should continue to long poll via the stream client to keep data flowing to their application in real time.

## Sending Data to Livefyre

The [`WriteClient`](https://github.com/Livefyre/StreamHub-Android-SDK/blob/gradle/src/main/java/com/livefyre/android/core/WriteClient.java) is used to send data to Livefyre (like, new comments, etc). All methods require a user token.
Responses to actions sent via the WriteClient will be sent directly back. They will also be sent down via the StreamClient, so be sure to check for duplicate content.

# Clients

The StreamHub Android SDK exposes several Client classes that can be used to request StreamHub APIs.

* [`BootstrapClient`](http://livefyre.github.com/StreamHub-Android-SDK/com/livefyre/streamhub_android_sdk/BootstrapClient.html) - Get recent Content and metadata about a particular Collection

* [`PublicAPIClient`](http://livefyre.github.com/StreamHub-Android-SDK/com/livefyre/streamhub_android_sdk/PublicAPIClient.html) - Request the Hottest Collections in a Network or get recent Content from a specific user

* [`StreamClient`](http://livefyre.github.io/StreamHub-Android-SDK/com/livefyre/streamhub_android_sdk/StreamClient.html) - Poll a Collection to retrieve new, updated, and deleted Content when it becomes available.

* [`WriteClient`](http://livefyre.github.io/StreamHub-Android-SDK/com/livefyre/streamhub_android_sdk/WriteClient.html) - Post Content, flag Content, like/unlike Content in a Collection. User authentication tokens will generally be required to use methods in this class.
