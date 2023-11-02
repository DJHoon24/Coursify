package cs346.model

import kotlin.test.*

class NoteTest {
    private lateinit var notesList: MutableList<Note>

    @BeforeTest
    fun setUp() {
        notesList = mutableListOf()
    }

    @AfterTest
    fun tearDown() {
        notesList.clear()
    }

    @Test
    fun testAddNotes() {
        notesList.add("NOTE 1", "content")
        notesList.add("NOTE 2", "2nd note")
        assertEquals(2, notesList.size)
        assertEquals("NOTE 1", notesList[0].title)
        assertEquals("content", notesList[0].content)
        assertEquals(1, notesList[0].id)
        assertEquals("NOTE 2", notesList[1].title)
        assertEquals("2nd note", notesList[1].content)
        assertEquals(2, notesList[1].id)
    }

    @Test
    fun testEditNotes() {
        notesList.add("NOTE 1", "content")
        assertEquals(1, notesList.size)
        assertEquals("NOTE 1", notesList[0].title)
        assertEquals("content", notesList[0].content)
        val originalID = notesList[0].id
        val originalModifiedDate = notesList[0].lastModifiedDateTime
        val originalCreationDate = notesList[0].createdDateTime
        Thread.sleep(500)
        notesList.edit("NEW TITLE", "NEW CONTENT", 1)
        assertEquals(1, notesList.size)
        assertEquals("NEW TITLE", notesList[0].title)
        assertEquals("NEW CONTENT", notesList[0].content)
        assertEquals(originalID, notesList[0].id)
        assertEquals(originalCreationDate, notesList[0].createdDateTime)
        assertNotEquals(originalModifiedDate, notesList[0].lastModifiedDateTime)
    }
}