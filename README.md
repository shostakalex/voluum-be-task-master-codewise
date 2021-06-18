# Voluum backend developer task

Write a component which receives messages containing text and type. 
The component provides two read operations:

* query for the snapshot of the last 100 received messages
* query for the snapshot of messages with a specified type within the last 100 messages

Messages older than 5 min should not be taken into consideration (e.g. if there were only 30 messages during last 5 minutes then return those 30).
There are multiple (possibly lots of) writers and readers.

The solution should implement pl.codewise.voluum.task.MessageQueue interface, you can use only the standard java library there. 
You can use whatever you want in tests.
