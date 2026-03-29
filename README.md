# UBC Science Academic Placement Planner

## What does this planner do?

The **UBC Science Academic Placement Planner** is a desktop application designed to help first-year UBC Science students navigate the competitive process of declaring a major. The application provides a centralized platform where students can:

* **1. Rank Specializations:** View a list of science majors ranked by their historical competitiveness and GPA requirements.
* **2. Prerequisite Check:** Input current courses and intended majors to identify missing required courses based on UBC’s official specialization requirements.
* **3. Probability Estimation:** Use a statistical model to calculate the likelihood of admission into specific majors based on the user's current GPA, accounting for historical "turbulence" and year-to-year fluctuations.
* **4. Strategic Recommendations:** Categorize majors into "Top Choice" (ambitious) and "Insurance Choice" (safe) based on the user's academic profile.

## Target users

The primary users are **first-year UBC Science students** (both domestic and international) who are preparing to apply for their specializations in June. It is also useful for students in other faculties looking to transfer into Science.

## Why this planner application is practical and useful

Many first-year students, including myself, find the specialization application process stressful and opaque. While historical cut-off grades are available, there isn't a tool that systematically aggregates this data to provide a personalized probability of entry. This project is meaningful because it transforms raw data into actionable advice, helping peers make informed decisions about their academic futures and reducing the anxiety associated with "second-year placement."

---

## User Stories

- As a user, firstly, I want to be able to **add a course** (including course code, credits, and grade) to my **academic record** so I can track my current standing, so that I can start my planning process.
- As a user, I want to be able to **view a list** of all science specializations and their historical **average** GPA cut-offs.
- As a user, I want to be able to **view a rank** of all science specializations based on their historical competitiveness.
- As a user, I want to select a specific major and see a **calculated probability percentage** of admission based on my current profile.
- As a user, I want the application to **identify and display a list of missing prerequisite courses** for my target specialization based on the courses I have already entered.
- As a user, I want to be able to see the **top searched specialization** on this app.
- As a user, I want to be able to save my current student profile (including all my added courses) to a file so that I can keep my progress for next time.
- As a user, I want to be able to load my previous student profile from a file so that I can continue my academic planning from where I left off.

# Instructions for End User
- You can view the panel that displays the courses that have already been added to the profile by looking at the central scrollable list titled "My Academic Record".
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by typing the course code, credits, and grade into the respective text fields at the bottom, and then clicking the "Add Course" button.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the "Predict Admission" button, which will calculate your admission probability based on your current courses and display it in a pop-up dialog.
- You can locate my visual component by looking at the very top of the application window, where the UBC Science logo image is prominently displayed.
- You can save the state of my application by clicking the "Save Data" button located at the bottom right of the control panel.
- You can reload the state of my application by clicking the "Load Data" button located at the bottom right of the control panel.


# Phase 4: Task 2
Representative sample of events logged by the application:

Event Log:
Sat Mar 28 18:20:00 PDT 2026
Event log cleared.
Sat Mar 28 18:17:17 PDT 2026
Added course: MATH200 to the profile.
Sat Mar 28 18:20:20 PDT 2026
Added course: CPSC121 to the profile.

# Phase 4: Task 3

If I had more time to work on the project, I would perform a major refactoring to address issues of low cohesion and high coupling, specifically by introducing the **Observer Pattern** and a **Controller/Manager** class.

Currently, the `GraphicalPlannerApp` acts somewhat like a "God Class". It handles UI rendering, directly interacts with `JsonReader` and `JsonWriter` for data persistence, and must manually refresh its own display components whenever a `Course` is added to the `StudentProfile`. This design violates the Single Responsibility Principle, resulting in low cohesion within the UI layer and tight coupling across the UI, Model, and Persistence layers.

To improve this design, my first step would be implementing the **Observer Pattern** to decouple the Model from the UI. I would make `StudentProfile` the *Subject* (Observable) and the `GraphicalPlannerApp` (or its specific panels) the *Observer*. When a course is added, the `StudentProfile` would automatically call `notifyObservers()`. The UI would simply listen for this event and update itself. This removes the need for the UI to manually manage data synchronization, significantly reducing coupling. 

Secondly, I would extract the file saving and loading logic into a new `ProfileManager` class. This manager would handle all the `try-catch` blocks and interactions with the persistence layer. By doing this, the UI classes would only need to communicate with the `ProfileManager`, achieving much higher cohesion (UI only cares about visuals and user inputs) and keeping the architecture clean and modular.