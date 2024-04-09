package com.example.practicia

import IaMessageModel
import org.junit.Test
import org.junit.Assert.*

internal class MainActivityTest {

    // Ceci est votre viewModel
    private var iaMessageViewModel = IaMessageViewModel()

    // ceci est votre model
    private var goodIAModel = IaMessageModel("messageuuh", 200)
    private var messageVide = IaMessageModel("", 200)
    private var badIAModel = IaMessageModel("messageuuuh", 500)

    @Test
    fun isResponseCode200() {
        // Appeler la fonction qui check le ResponseCode dans votre viewModel
        // check si isResponse est 200 renvoyer true sinon false
        val responseCode = goodIAModel.responseCode
        val expected = true
        val actual = iaMessageViewModel.checkResponseCode(200, responseCode)
        assertEquals(expected, actual)
    }

    @Test
    fun `check si message vide`() {
        // check si les messages sont vides sur les différents models
        //assertFalse(goodIAModel.msgFromIA)
       // assertNotNull(goodIAModel.msgFromIA, goodIAModel)
    }

    @Test
    fun `check si message null`() {
        // check si les messages sont null sur les différents models
    }
}