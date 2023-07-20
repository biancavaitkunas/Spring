	package br.com.trier.spring.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dados")

public class DadosResource {
	
	List <String> res = new ArrayList<String>();
	
	@GetMapping("/lanca/{qtDados}/{aposta}")
	public List <String> lancaDado(@PathVariable (name = "qtDados")Integer qtDados, @PathVariable (name = "aposta") Integer aposta) {
		
		Integer dados = 0;
		Integer somaDados = 0;
		
		if (validaDado(qtDados)) {
			
			Random jogar = new Random();
			
			for (int i = 0; i < qtDados; i++) {
				dados = (int) jogar.nextInt(1, 6);
				res.add("Valor do dado " + i + ": " + String.valueOf(dados));
				somaDados += dados;	
			}
			res.add("Soma dos dados: " + String.valueOf(somaDados));
			
		}
		
		if (validaAposta(qtDados, aposta)) {
			if (somaDados == aposta) {
				res.add("Acertou a aposta!");
			} else {
				res.add("Errou a aposta!");
			}
		}
		
		res.add("Percentual: " + String.valueOf(percentual(somaDados, aposta))+ "% \n");
		
		return res;
		
	}
	
	public Boolean validaDado(Integer qtDados) {
		return qtDados >= 1 && qtDados <= 4;
	}
	
	public Boolean validaAposta(Integer qtDados, Integer aposta) {
		return aposta >= qtDados*1 && aposta <= qtDados*6;
		
	}
	
	public Integer percentual(Integer soma, Integer aposta) {
		Integer percentual = aposta*100/soma;
		return percentual;
	}

}
