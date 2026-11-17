# Android Fitness Maps & Sensors Prototype

A legacy Android study project exploring a fitness-oriented map experience, device location and hardware sensor readings.

> **Status:** Historical prototype, last updated in 2017. The project uses an obsolete Android Gradle and support-library stack; a current build has not been verified.

## What the code explores

- Google Maps screens and map markers.
- Device location updates through the fused location provider.
- Route and map overlays using Google Maps polylines and polygons.
- Location clustering for map markers.
- Android sensor discovery and event handling through `SensorManager`.
- Accelerometer / sensor readings and a RecyclerView-based sensor list.
- Basic exercise-session state represented by start time, end time and completion status.

The codebase is an exploration of mapping and mobile sensors; it is not presented as a production-ready tracker or as a complete workout-recording system.

## Technology

- Java and native Android
- Google Maps SDK
- Google Play Services Location
- Google Maps Android Utils
- Android `SensorManager` APIs
- RecyclerView
- Android Gradle Plugin 2.3.1 and Gradle 3.3

## Project layout

```text
app/src/main/java/com/example/iossenac/monitorfitness/
  HomeActivity.java
  MapsActivity.java
  ListSensorActivity.java
  DadoExercicio.java
  MyItem.java
  AdapterRecyclerView.java
  CustoInfoWindowAdapter.java
app/src/main/res/layout/
```

## Running the historical project

This repository uses Android Gradle Plugin 2.3.1 and Gradle 3.3. It will likely require a legacy Android toolchain or migration before it can be built with a current Android Studio release.

A Google Maps API key is required for map rendering. Use a newly created key restricted to the correct Android application ID and signing certificate. Keep local machine configuration out of version control.

## Production considerations

Before any real-world use, the prototype would need current Android dependencies, permission and background-location handling, lifecycle-safe sensor subscriptions, battery-impact testing, privacy disclosures, automated tests and secure API-key management.
