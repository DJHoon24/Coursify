package cs346.model

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

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
        notesList.addNote("NOTE 1", "content")
        notesList.addNote("NOTE 2", "2nd note")
        assertEquals(2, notesList.size)
        assertEquals("NOTE 1", notesList[0].title)
        assertEquals("content", notesList[0].content)
        assertEquals("NOTE 2", notesList[1].title)
        assertEquals("2nd note", notesList[1].content)
    }

    @Test
    fun testEditNotes() {
        notesList.addNote("NOTE 1", "content")
        val originalNote = notesList[0]
        val originalID = originalNote.id
        notesList.edit("NEW TITLE", "NEW CONTENT", originalID)
        val editedNote = notesList[0]
        assertEquals("NEW TITLE", editedNote.title)
        assertEquals("NEW CONTENT", editedNote.content)
        assertEquals(originalID, editedNote.id)
    }

    @Test
    fun testGetById() {
        notesList.addNote("NOTE 1", "content")
        val originalNote = notesList[0]
        val originalID = originalNote.id
        val note = notesList.getById(originalID)
        assertNotNull(note)
        assertEquals(originalNote, note)
    }

    @Test
    fun testGetById_NotFound() {
        val note = notesList.getById(999)
        assertNull(note)
    }
}