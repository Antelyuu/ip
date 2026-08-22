# UI test plan

Each command is run in a fresh process. Expected output is compared exactly.

## Test case: exit immediately

Aim: Verify that the program starts and exits when the user enters `bye`.

### Command
```text
bye
```

### Expected output
```text
____________________________________________________________
 __  __              _
|  \/  | ___  _ __  | | _____ _   _
| |\/| |/ _ \| '_ \ | |/ / _ \ | | |
| |  | | (_) | | | ||   <  __/ |_| |
|_|  |_|\___/|_| |_||_|\_\___|\__, |
                              |___/

Hello! I'm Monkey, your cheeky little assistant.
What can I do for you today?
____________________________________________________________
____________________________________________________________
Bye! Keep swinging, and I hope to see you again soon!
____________________________________________________________
```

## Test case: delete a task and renumber remaining tasks

Aim: Verify that a task can be deleted and that the remaining tasks are renumbered.

### Command
```text
todo first
todo second
delete 1
list
bye
```

### Expected output
```text
____________________________________________________________
 __  __              _
|  \/  | ___  _ __  | | _____ _   _
| |\/| |/ _ \| '_ \ | |/ / _ \ | | |
| |  | | (_) | | | ||   <  __/ |_| |
|_|  |_|\___/|_| |_||_|\_\___|\__, |
                              |___/

Hello! I'm Monkey, your cheeky little assistant.
What can I do for you today?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] first
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] second
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
  [T][ ] first
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] second
____________________________________________________________
____________________________________________________________
Bye! Keep swinging, and I hope to see you again soon!
____________________________________________________________
```

## Test case: reject non-numeric mark and preserve task state

Aim: Verify that an invalid `mark` argument is rejected without changing a valid task.

### Command
```text
todo buy milk
mark abc
list
bye
```

### Expected output
```text
____________________________________________________________
 __  __              _
|  \/  | ___  _ __  | | _____ _   _
| |\/| |/ _ \| '_ \ | |/ / _ \ | | |
| |  | | (_) | | | ||   <  __/ |_| |
|_|  |_|\___/|_| |_||_|\_\___|\__, |
                              |___/

Hello! I'm Monkey, your cheeky little assistant.
What can I do for you today?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] buy milk
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
OOPS! Monkey says: That banana-shaped task number does not look right. Use a number after 'mark'.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________________________
____________________________________________________________
Bye! Keep swinging, and I hope to see you again soon!
____________________________________________________________
```

## Test case: reject invalid task indexes

Aim: Verify that zero and out-of-range task numbers do not alter the task list.

### Command
```text
todo submit
mark 0
mark 2
list
bye
```

### Expected output
```text
____________________________________________________________
 __  __              _
|  \/  | ___  _ __  | | _____ _   _
| |\/| |/ _ \| '_ \ | |/ / _ \ | | |
| |  | | (_) | | | ||   <  __/ |_| |
|_|  |_|\___/|_| |_||_|\_\___|\__, |
                              |___/

Hello! I'm Monkey, your cheeky little assistant.
What can I do for you today?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] submit
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
That task number does not exist.
____________________________________________________________
____________________________________________________________
That task number does not exist.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] submit
____________________________________________________________
____________________________________________________________
Bye! Keep swinging, and I hope to see you again soon!
____________________________________________________________
```

## Test case: interleave mark, invalid unmark, and unmark

Aim: Verify that a valid completion persists through an invalid command and can then be reversed by a valid command.

### Command
```text
todo read
mark 1
unmark nope
list
unmark 1
list
bye
```

### Expected output
```text
____________________________________________________________
 __  __              _
|  \/  | ___  _ __  | | _____ _   _
| |\/| |/ _ \| '_ \ | |/ / _ \ | | |
| |  | | (_) | | | ||   <  __/ |_| |
|_|  |_|\___/|_| |_||_|\_\___|\__, |
                              |___/

Hello! I'm Monkey, your cheeky little assistant.
What can I do for you today?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read
____________________________________________________________
____________________________________________________________
OOPS! Monkey says: This monkey needs a valid task number after 'unmark'.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
  [T][ ] read
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read
____________________________________________________________
____________________________________________________________
Bye! Keep swinging, and I hope to see you again soon!
____________________________________________________________
```

## Test case: add a deadline

Aim: Verify that a deadline command stores both its description and due date/time.

### Command
```text
deadline submit report /by 11/10/2019 5pm
list
bye
```

### Expected output
```text
____________________________________________________________
 __  __              _
|  \/  | ___  _ __  | | _____ _   _
| |\/| |/ _ \| '_ \ | |/ / _ \ | | |
| |  | | (_) | | | ||   <  __/ |_| |
|_|  |_|\___/|_| |_||_|\_\___|\__, |
                              |___/

Hello! I'm Monkey, your cheeky little assistant.
What can I do for you today?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] submit report (by: 11/10/2019 5pm)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] submit report (by: 11/10/2019 5pm)
____________________________________________________________
____________________________________________________________
Bye! Keep swinging, and I hope to see you again soon!
____________________________________________________________
```

## Test case: add and list a todo

Aim: Verify that a todo command creates a typed todo and that it is displayed in the list.

### Command
```text
todo borrow book
list
bye
```

### Expected output
```text
____________________________________________________________
 __  __              _
|  \/  | ___  _ __  | | _____ _   _
| |\/| |/ _ \| '_ \ | |/ / _ \ | | |
| |  | | (_) | | | ||   <  __/ |_| |
|_|  |_|\___/|_| |_||_|\_\___|\__, |
                              |___/

Hello! I'm Monkey, your cheeky little assistant.
What can I do for you today?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Bye! Keep swinging, and I hope to see you again soon!
____________________________________________________________
```

## Test case: add an event

Aim: Verify that an event command stores its description, start, and end date/time values.

### Command
```text
event project meeting /from Mon 2pm /to 4pm
list
bye
```

### Expected output
```text
____________________________________________________________
 __  __              _
|  \/  | ___  _ __  | | _____ _   _
| |\/| |/ _ \| '_ \ | |/ / _ \ | | |
| |  | | (_) | | | ||   <  __/ |_| |
|_|  |_|\___/|_| |_||_|\_\___|\__, |
                              |___/

Hello! I'm Monkey, your cheeky little assistant.
What can I do for you today?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye! Keep swinging, and I hope to see you again soon!
____________________________________________________________
```
