package com.borisov.DZ_4.assembler;

import com.borisov.DZ_4.controllers.UsersController;
import com.borisov.DZ_4.dto.UserResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler
        implements RepresentationModelAssembler<UserResponseDTO, EntityModel<UserResponseDTO>> {

    @Override
    public EntityModel<UserResponseDTO> toModel(UserResponseDTO user) {
        return EntityModel.of(user,
                linkTo(methodOn(UsersController.class).getUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UsersController.class).getUsers()).withRel("users"),
                linkTo(methodOn(UsersController.class).delete(user.getId())).withRel("delete"),
                linkTo(methodOn(UsersController.class).update(user.getId(), null, null))
                        .withRel("update")
        );
    }
}

