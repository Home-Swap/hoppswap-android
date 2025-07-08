package com.hoppswap.data.auth.local

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.hoppswap.data.auth.model.User
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class AuthSharedPrefsTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor
    private lateinit var json: Json
    private lateinit var authSharedPrefs: AuthSharedPrefs

    private val user = User(
        email = "test@example.com",
        name = "Test User",
        city = "Tokyo",
        age = "34",
        bio = "Loves art and food",
        profilePhoto = "https://example.com/photo.jpg",
        successfulSwaps = 10
    )

    @Before
    fun setup() {
        sharedPreferences = mock()
        editor = mock()
        json = Json { ignoreUnknownKeys = true }
        whenever(sharedPreferences.edit()).thenReturn(editor)
        whenever(editor.putString(any(), any())).thenReturn(editor)
        whenever(editor.remove(any())).thenReturn(editor)
        authSharedPrefs = AuthSharedPrefs(sharedPreferences, json)
    }

    @Test
    fun givenUser_whenSaveUser_thenUserIsSavedAsJson() {
        // When
        authSharedPrefs.saveUser(user)

        // Then
        val expectedJson = json.encodeToString(user)
        verify(editor).putString("user", expectedJson)
        verify(editor).apply()
    }

    @Test
    fun givenSavedUser_whenGetUser_thenReturnsDeserializedUser() {
        // Given
        val expectedJson = json.encodeToString(user)
        whenever(sharedPreferences.getString("user", null)).thenReturn(expectedJson)

        // When
        val result = authSharedPrefs.getUser()

        // Then
        assertEquals(user, result)
    }

    @Test
    fun givenCorruptJson_whenGetUser_thenReturnsNull() {
        // Given
        whenever(sharedPreferences.getString("user", null)).thenReturn("{bad json}")

        // When
        val result = authSharedPrefs.getUser()

        // Then
        assertNull(result)
    }

    @Test
    fun givenToken_whenSaveSessionToken_thenTokenIsSaved() {
        // When
        authSharedPrefs.saveSessionToken("the-token")

        // Then
        verify(editor).putString("session_token", "the-token")
        verify(editor).apply()
    }

    @Test
    fun givenSavedToken_whenGetSessionToken_thenReturnsToken() {
        // Given
        whenever(sharedPreferences.getString("session_token", null)).thenReturn("the-token")

        // When
        val result = authSharedPrefs.getSessionToken()

        // Then
        assertEquals("the-token", result)
    }

    @Test
    fun whenLogout_thenUserAndTokenAreRemoved() {
        // When
        authSharedPrefs.logout()

        // Then
        verify(editor).remove("user")
        verify(editor).remove("session_token")
        verify(editor).apply()
    }
}