Interfaces:
	MusicOperations<K, U>
	MusicBlock<T>
	MusicBlockHead<T> extends MusicBlock<T>
	ViewModelOperations
	CompositionBuilder
	DynamicViewInterface
	EditorCtrl
	IPlayhead

A Song is a representation of a single song for editing.
As a Song implements the MusicOperations interface, it is able
	to output its state as a string, add, remove, and edit notes,
	load a new state or add other songs states to its own
	(simultaneously or consecutively), get the blocks at a specific
	beat index, get the blocks of a specific pitch, get the head
	note for any NoteHead or NoteSustain in the song, and
	set/change the number of beats in the song.

The MusicOperations interface has two generics representing the
	blocks that make up the song (for Song these are MusicBlocks),
	and the container for pitch information in these blocks (Notes).

All MusicBlocks implement the MusicBlock interface, so they are able
	to output their string representations, get their head notes,
	and set and get their beat information.

The MusicBlockHead adds the functionality of setting/getting its
	duration and getting/setting its list of sustain blocks tailing it.

Splitting the music into blocks like this allows for easy access at any
beat, or of any specific pitch along an entire song. Similarly it allows
for easy addition/subtraction/editting of blocks anywhere in the song.

The Beat class acts as part of the Song class, allowing for more control
over the individual beats of a song, rather than just having a matrix
of the blocks that has less fine control. Thus, beats are able to store
their indexes and more information about their range.

WHAT WE ADDED:
The purpose of this assignment is to combine the visual and midi views
into one composite view, capable of interacting with both.

Adding these interactions involved creating a new, composite view,
that holds references to both kinds of view, and delegates tasks to the correct
view for an action.

These actions are created by mapping Runnable or Consumer objects (instantiated
with lambda statements) to key, mouse, or button events. These are then passed
to the gui, which is able to determine if any of these events occur, in object
that extend Keyboard, Mouse, and Button listener classes. The controller
then decides what to do for each event. For example, pressing spacebar, when the
gui is in focus, send an event to the controller to start/stop the gui's playhead
that is moving, along with the midi view's sequencer that plays the music.

A Timer is also added, that syncronizes the location of the playhead bar
in the gui with the current beat the midi sequencer is at.

Also added is the functionality to add multiple, overlapping notes, to a song.
This expanded the space requirements for a song, but by only collecting the
noteheads from a song to determine where each note start/ends, creating a midi
track or displaying the notes is less resource intensive than before.

The playhead object is passed between the different views, and keeps track of its
current beat, as well as being able to create a rectangle to display on the gui.
This rectangle also is used to only update the part of the gui with the playhead
during playback.