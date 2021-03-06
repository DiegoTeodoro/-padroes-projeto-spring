package com.produtos.apirest.resources;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.model.Produto;
import com.produtos.apirest.repository.ProdutoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST Produtos")
@CrossOrigin(origins="*")
public class ProdutoController {

	@Autowired
	ProdutoRepository produtoRepository;

	@GetMapping("/produtos")
	@ApiOperation(value="Retorna uma lista de produtos")
	public ResponseEntity<List<Produto>> getAllProdutos() {
		List<Produto> produtoList = produtoRepository.findAll();
		if (produtoList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Produto>>(produtoList, HttpStatus.OK);
		}
	}

	@GetMapping("/produtos/{id}")
	@ApiOperation(value="Retorna uma produto unico")
	public ResponseEntity<Produto> getOneProduto(@PathVariable(value = "id") long id) {
		Optional<Produto> produtoO = produtoRepository.findById(id);
		if (!produtoO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(produtoO.get(), HttpStatus.OK);
		}

	}

	@PostMapping("/produtos")
	@ApiOperation(value="Salva um produto")
	public Produto criar(@RequestBody Produto produto, HttpServletResponse response){
		return produtoRepository.save(produto);
		
	}

	@DeleteMapping("/produtos/{id}")
	@ApiOperation(value="Deleta um produto")
	public ResponseEntity<?> deleteProduto(@PathVariable(value = "id") long id) {
		Optional<Produto> produtoO = produtoRepository.findById(id);

		if (!produtoO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			produtoRepository.delete(produtoO.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@PutMapping("/produtos")
	@ApiOperation(value="Atualiza um produto")
	public ResponseEntity<Produto> updateProduto(@PathVariable(value = "id") long id,
			@RequestBody @Validated Produto produto) {
		Optional<Produto> produtoO = produtoRepository.findById(id);
		if (!produtoO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			produto.setId(produtoO.get().getId());
			return new ResponseEntity<Produto>(produtoRepository.save(produto), HttpStatus.OK);
		}
	}

}
