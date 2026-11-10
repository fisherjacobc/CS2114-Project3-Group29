# Queue Ride
## Virginia Tech | CS2114 | Project 3 | Group 29
### Members

- Jacob Fisher ([@fisherjacobc](https://github.com/fisherjacobc))
- Aishwarya Shah ([@aishwaryashah28](https://github.com/aishwaryashah28))

## Overview
Queue Ride is an interactive, console-based queue system written in Java that simulates a car rideshare service, similar to Uber. Users can be prompted to enter their name and destination to request a ride. Once they enter the car, the first person who entered will be the first one dropped off. The second person is be dropped off next, and so on. Users can also be able to see who is in the car and who will be dropped off next.

## How to Compile & Run
Open up VS Code with the Java Language Support Extension Pack, and head to [`Main.java`](/src/rideshare/Main.java). From there, you can click on 'Run' and go through the simulation.

VS Code will automatically compile for you, but if you wish to compile manually, you can use `javac **/*.java`.

To run tests, head to the Testing tab (looks like a beaker icon), and you can run the full suite of tests all at once and get a code coverage report.

## Reflection

### What changed from the spec
- **`Node` and `Queue` are generic** so the same queue works for both the waiting line and the cars, and could be tested before `Passenger` existed
- **`Queue` accepts `null`**, so null checks moved up into `Car` and `RideshareModel`, which know what a valid entry is
- **Empty queues and cars return `null`** instead of throwing, since the spec contradicted itself and `null` is simpler for the controller to handle
- **A full car isn't an exception**: the waiting line has no limit, and seat checks happen when assigning passengers to cars
- **`Car` has no stored destination**: it reads the destination of its front passenger so it never goes out of date instead of trying to update the same value in two places
- **`initializeCars` won't replace occupied cars**, so passengers can't be silently deleted
- **Added `getElapsedMinutes()`** so the controller can display the clock
- **Menu input is validated in the view and controller together**, and a quit option was added

### Did the model stand alone?
- Yes, the model classes never print or read input and don't depend on the view or controller
- All existing tests run without the view or controller

### What happened to the unknowns
- **Queue behavior after emptying:** figured out and tested
- **Testing the last drop-off:** tested for `Car`, still needs model-level tests
- **What triggers drop-offs:** for now the user drops off passengers manually, and clock-driven drop-offs are planned
- **Assigning passengers to cars:** passengers fill the lowest-numbered car with a free seat
- **Estimating arrival times:** each stop takes a random time between a set minimum and maximum, and the estimate is shown as that range

### What doesn't work yet
- No model tests, and no tests for the controller/view
- Assigning a passenger before any cars exist gives a misleading message
- The roadmap view is only a placeholder
- Simulating time only advances the clock
- Dropped-off passengers aren't recorded
- The controller doesn't reject `null` arguments
- Ending input with Ctrl+D crashes the program

## Next Steps

### Finishing the MVP
1. Handle the case where no cars have been set up *(Owner: Jacob)*
2. Write `RideshareModelTest` *(Owner: Aishwarya)*
3. Fill the remaining `Queue` test gaps *(Owner: Jacob)*
4. Reject `null` arguments in the controller *(Owner: Jacob)*
5. Handle end of input without crashing *(Owner: Jacob and Aishwarya)*
6. Walk through the full program together, including bad inputs *(Owner: Jacob and Aishwarya)*

### Change feature: drop-offs driven by the clock
- **What it is:** each stop takes a random amount of time between a set minimum and maximum (X to Y), and advancing the clock drops off passengers as stops are reached
- **Why it's not trivial:** each car needs its own countdown, one time step can cause many drop-offs, and the results need to reach the view
- **What it touches:** `Car`, `RideshareModel.updateTravelTime`, the controller's time simulation, and a new view method for showing drop-offs
- **What shouldn't change:** `Queue`, `Node`, `Passenger`, and manual drop-offs
- *(Owner: Aishwarya for the model; Jacob for the controller and view)*

### Stretch goals
- **Committed: multiple cars**, which already works but needs tests *(Owner: Aishwarya)*
- **Committed: time simulation**, as the change feature above
- **Committed: simple roadmap view** showing each car's upcoming stops *(Owner: Jacob and Aishwarya)*
- **Stretch: arrival-time estimates**, shown as a range: a passenger *n* stops away arrives in a random amount of time, with the actual time picked at random within each stop's range

### New tests needed
- **`RideshareModelTest`** for the spec's test plan plus the changes above *(Owner: Aishwarya)*
- **`QueueTest`** for `size()` and independent queues *(Owner: Jacob)*
- **`CarTest`** for clock-driven drop-offs *(Owner: Aishwarya)*
