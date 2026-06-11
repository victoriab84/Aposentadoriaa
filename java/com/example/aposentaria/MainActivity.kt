package com.example.aposentadoria

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aposentadoria.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val IDADE_MINIMA_HOMEM = 65
        private const val IDAGE_MINIMA_MULHER = 62
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarSpinnerGenero()
        configurarBotaoCalcular()
    }

    private fun configurarSpinnerGenero() {
        val generos = listOf("Selecione", "Masculino", "Feminino")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spGenero.adapter = adapter
    }

    private fun configurarBotaoCalcular() {
        binding.btnCalcular.setOnClickListener {
            calcularAposentadoria()
        }
    }

    private fun calcularAposentadoria() {
        val idadeTexto = binding.etIdade.text.toString().trim()

        if (idadeTexto.isEmpty()) {
            binding.etIdade.error = "Informe a idade."
            return
        }

        val idade = idadeTexto.toIntOrNull()
        if (idade == null || idade <= 0 || idade > 120) {
            binding.etIdade.error = "Idade inválida."
            return
        }

        val generoSelecionado = binding.spGenero.selectedItem?.toString() ?: ""
        if (generoSelecionado == "Selecione" || generoSelecionado.isEmpty()) {
            Toast.makeText(this, "Selecione o gênero.", Toast.LENGTH_SHORT).show()
            return
        }

        val idadeMinima = if (generoSelecionado == "Masculino") IDADE_MINIMA_HOMEM else IDAGE_MINIMA_MULHER
        val anosRestantes = idadeMinima - idade

        if (anosRestantes <= 0) {
            binding.tvResultado.text = "Você já pode se aposentar!"
        } else {
            binding.tvResultado.text = "Faltam $anosRestantes anos para a aposentadoria."
        }
    }
}