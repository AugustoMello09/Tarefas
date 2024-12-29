package io.gitHub.AugustoMello09.tarefas.services;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.gitHub.AugustoMello09.tarefas.domain.dtos.CargoDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.tarefas.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Cargo;
import io.gitHub.AugustoMello09.tarefas.domain.entities.Usuario;
import io.gitHub.AugustoMello09.tarefas.repositories.CargoRepository;
import io.gitHub.AugustoMello09.tarefas.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.DataIntegratyViolationException;
import io.gitHub.AugustoMello09.tarefas.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final CargoRepository cargoRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final ImgService imgService;
	
	@Transactional(readOnly = true)
	public UsuarioDTO findById(UUID id) {
		Optional<Usuario> obj = repository.findById(id);
		Usuario entity = obj.orElseThrow(()-> new ObjectNotFoundException("Usuário não encontrado"));
		return new UsuarioDTO(entity);
	}
	
	@Transactional
	public UsuarioDTO create(UsuarioDTOInsert objDto) {	
		emailAlreadyExists(objDto);
		Usuario usuario = new Usuario();
		usuario.setName(objDto.getName());
		usuario.setEmail(objDto.getEmail());
		usuario.setPassword(passwordEncoder.encode(objDto.getSenha()));
		assignRole(usuario, objDto);
		usuario.setNotification(false);
		usuario.setImgUrl(null);
		repository.save(usuario);
		return new UsuarioDTO(usuario);
	}
	
	@Transactional
	public void updateUser(UsuarioDTO usuarioDTO, UUID id) {
		Usuario entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		entity.setName(usuarioDTO.getName());
		entity.setEmail(usuarioDTO.getEmail());
		repository.save(entity);
		
	}
	
	@Transactional
	public UsuarioDTO activateNotification(UUID id) {
		Usuario entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		entity.setNotification(!entity.getNotification());
		repository.save(entity);
		return new UsuarioDTO(entity);
	}
	
	
	protected void assignRole(Usuario usuario, UsuarioDTO objDto) {
		for(CargoDTO cargos : objDto.getCargos()) {
			Cargo cargo = cargoRepository.findById(cargos.getId())
					.orElseThrow(() -> new ObjectNotFoundException("Cargo não encontrado"));
			usuario.getCargos().add(cargo);
		}
	}
	
	protected void emailAlreadyExists(UsuarioDTO usuarioDTO) {
		Optional<Usuario> entity = repository.findByEmail(usuarioDTO.getEmail());
		if (entity.isPresent() && !entity.get().getId().equals(usuarioDTO.getId())) {
			throw new DataIntegratyViolationException("Email já existe");
		}
	}
	
	public void uploadfile(UUID idUsuario, MultipartFile imagem) throws IOException {
		Usuario usuario = repository.findById(idUsuario)
				.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado. "));
	    BufferedImage img = imgService.getJpgImageFromFile(imagem);
	    BufferedImage resizedImg = imgService.resize(img, 400); 
	    BufferedImage croppedImg = imgService.cropSquare(resizedImg);
	        
	    String base64Image = convertToBase64(croppedImg, "jpg");
	    
	    usuario.setImgUrl(base64Image);
	    repository.save(usuario);
	}
	
	private String convertToBase64(BufferedImage img, String extension) throws IOException {
	    InputStream is = imgService.getInputStream(img, extension);
	    byte[] bytes = is.readAllBytes();
	    return Base64.getEncoder().encodeToString(bytes);
	}
	
}
