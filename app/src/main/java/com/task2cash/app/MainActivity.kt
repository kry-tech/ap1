package com.task2cash.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var saldo = 0f
    private var ganhoDiario = 0f
    private val limiteDiario = 1.0f
    private val valorAnuncio = 0.01f

    private lateinit var tvSaldo: TextView
    private lateinit var tvLimiteDiario: TextView
    private lateinit var btnSacar: Button
    private lateinit var btnHistorico: Button
    private lateinit var btnAnuncio: Button
    private lateinit var btnTarefaRapida: Button
    private lateinit var btnConvidar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSaldo = findViewById(R.id.tv_saldo)
        tvLimiteDiario = findViewById(R.id.tv_limite_diario)
        btnSacar = findViewById(R.id.btn_sacar)
        btnHistorico = findViewById(R.id.btn_historico)
        btnAnuncio = findViewById(R.id.btn_anuncio)
        btnTarefaRapida = findViewById(R.id.btn_tarefa_rapida)
        btnConvidar = findViewById(R.id.btn_convidar)

        atualizarSaldo()
        atualizarLimite()

        btnSacar.setOnClickListener {
            if (saldo >= 1.0f) {
                Toast.makeText(this, "Saque de R$ ${"%.2f".format(saldo)} realizado!", Toast.LENGTH_SHORT).show()
                saldo = 0f
                ganhoDiario = 0f
                atualizarSaldo()
                atualizarLimite()
            }
        }

        btnHistorico.setOnClickListener {
            Toast.makeText(this, "Histórico em breve", Toast.LENGTH_SHORT).show()
        }

        btnAnuncio.setOnClickListener {
            if (ganhoDiario < limiteDiario) {
                saldo += valorAnuncio
                ganhoDiario += valorAnuncio
                atualizarSaldo()
                atualizarLimite()
                Toast.makeText(this, "R$ 0,01 ganho!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Limite diário de R$ 1,00 atingido!", Toast.LENGTH_SHORT).show()
            }
        }

        btnTarefaRapida.setOnClickListener {
            Toast.makeText(this, "Tarefa rápida em breve", Toast.LENGTH_SHORT).show()
        }

        btnConvidar.setOnClickListener {
            Toast.makeText(this, "Convidar amigos em breve", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizarSaldo() {
        tvSaldo.text = "R$ ${"%.2f".format(saldo)}"
    }

    private fun atualizarLimite() {
        tvLimiteDiario.text = "Limite diário: R$ ${"%.2f".format(ganhoDiario)} / R$ 1,00"
        btnSacar.isEnabled = saldo >= 1.0f
    }

}
