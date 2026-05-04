package com.task2cash.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AdFeedActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdFeedAdapter
    private val anuncios = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_feed)

        MobileAds.initialize(this) {}

        for (i in 1..100) {
            anuncios.add(i)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AdFeedAdapter(anuncios) { posicao ->
            carregarAnuncio(posicao)
        }
        recyclerView.adapter = adapter
    }

    private fun carregarAnuncio(posicao: Int) {
        val adUnitId = "ca-app-pub-3940256099942544/5224354917"

        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(this, adUnitId, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                Toast.makeText(this@AdFeedActivity, "Erro ao carregar anúncio. Tente novamente.", Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                rewardedAd.show(this@AdFeedActivity) { rewardItem ->
                    Toast.makeText(this@AdFeedActivity, "Você ganhou R$ 0,01!", Toast.LENGTH_SHORT).show()

                    if (MainActivity.anunciosGlobal < 100) {
                        MainActivity.saldoGlobal += 0.01
                        MainActivity.anunciosGlobal++
                    }

                    adapter.marcarAssistido(posicao)
                }
            }
        })
    }
}
