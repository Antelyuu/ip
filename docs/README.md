# Monkey User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Snoozing deadlines

Snooze an incomplete deadline by providing its task number and a new future
date or date-time. The supported formats are `yyyy-MM-dd`, `d/M/yyyy`,
`yyyy-MM-dd HHmm`, and `d/M/yyyy HHmm`.

Example: `snooze 1 2026-09-20`

Only deadline tasks can be snoozed. Todos, events, completed deadlines, and
dates that are not in the future are rejected.

## Input validation and recovery

Monkey ignores leading and trailing whitespace and treats repeated whitespace
inside task descriptions as a single space. Commands such as `list` and `bye`
do not accept arguments, while `find` requires a keyword.

Use exactly one `/by` marker for a deadline. Events require one `/from` marker
followed by one `/to` marker. Date-based events must use a real date, and their
end must be later than their start. Identical tasks and descriptions containing
the storage delimiter (`|`) are rejected.

If saved data contains a malformed or duplicate line, Monkey keeps the valid
tasks and reports which data it ignored. If saving fails, the requested change
is not applied to the in-memory task list.

## Feature ABC

// Feature details


## Feature XYZ

// Feature details
