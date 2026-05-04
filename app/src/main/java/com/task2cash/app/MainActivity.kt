package com.task2cash.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var saldoText: TextView
    private lateinit var sacarButton: Button
    private lateinit var historicoButton: Button
    private lateinit var assistirButton: Button
    private lateinit var contadorText: TextView
    private lateinit var conteudoDinamico: LinearLayout

    private var saldo = 0.0
    private var anunciosAssistidosHoje = 0
    private var ultimaDataReset = System.currentTimeMillis()

    companion object {
        var saldoGlobal = 0.0
        var anunciosGlobal = 0
        var dataResetGlobal = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saldoText = findViewById(R.id.saldoText)
        sacarButton = findViewById(R.id.sacarButton)
        historicoButton = findViewById(R.id.historicoButton)
        assistirButton = findViewById(R.id.assistirButton)
        contadorText = findViewById(R.id.contadorText)
        conteudoDinamico = findViewById(R.id.conteudoDinamico)

        saldo = saldoGlobal
        anunciosAssistidosHoje = anunciosGlobal
        ultimaDataReset = dataResetGlobal

        verificarResetDiario()
        atualizarUI()
        mostrarConteudoPrincipal()

        assistirButton.setOnClickListener {
            val intent = Intent(this, AdFeedActivity::class.java)
            startActivity(intent)
        }

        sacarButton.setOnClickListener {
            if (saldo >= 1.0) {
                mostrarConteudoSacar()
            } else {
                Toast.makeText(this, "Saldo mínimo para saque é R$ 1,00", Toast.LENGTH_SHORT).show()
            }
        }

        historicoButton.setOnClickListener {
            mostrarConteudoHistorico()
        }
    }

    override fun onResume() {
        super.onResume()
        saldo = saldoGlobal
        anunciosAssistidosHoje = anunciosGlobal
        ultimaDataReset = dataResetGlobal
        verificarResetDiario()
        atualizarUI()
    }

    private fun verificarResetDiario() {
        val agora = System.currentTimeMillis()
        val umDiaEmMs = 24 * 60 * 60 * 1000L
        if (agora - ultimaDataReset >= umDiaEmMs) {
            anunciosAssistidosHoje = 0
            ultimaDataReset = agora
            anunciosGlobal = 0
            dataResetGlobal = agora
        }
    }

    private fun atualizarUI() {
        val saldoFormatado = String.format("%.2f", saldo)
        saldoText.text = "Saldo: R$ $saldoFormatado"

        if (saldo < 1.0) {
            saldoText.setTextColor(resources.getColor(android.R.color.holo_red_dark))
        } else {
            saldoText.setTextColor(resources.getColor(android.R.color.black))
        }

        contadorText.text = "Assistir Anúncios ($anunciosAssistidosHoje de 100)"
    }

    private fun mostrarConteudoPrincipal() {
        assistirButton.visibility = android.view.View.VISIBLE
        contadorText.visibility = android.view.View.VISIBLE
        conteudoDinamico.removeAllViews()
    }

    private fun mostrarConteudoSacar() {
        assistirButton.visibility = android.view.View.GONE
        contadorText.visibility = android.view.View.GONE
        conteudoDinamico.removeAllViews()

        val textView = TextView(this)
        textView.text = "Saque de R$ ${String.format("%.2f", saldo)} realizado com sucesso!"
        textView.textSize = 18f
        textView.setPadding(0, 32, 0, 0)
        conteudoDinamico.addView(textView)

        saldo = 0.0
        saldoGlobal = 0.0
        atualizarUI()
    }

    private fun mostrarConteudoHistorico() {
        assistirButton.visibility = android.view.View.GONE
        contadorText.visibility = android.view.View.GONE
        conteudoDinamico.removeAllViews()

        val textView = TextView(this)
        textView.text = "Histórico de saques:\nNenhum saque realizado ainda."
        textView.textSize = 18f
        textView.setPadding(0, 32, 0, 0)
        conteudoDinamico.addView(textView)
    }
}
