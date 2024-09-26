package br.com.sidroniolima.admin.infrastructure.api;

import br.com.sidroniolima.admin.domain.pagination.Pagination;
import br.com.sidroniolima.admin.infrastructure.castmember.models.CastMemberResponse;
import br.com.sidroniolima.admin.infrastructure.castmember.models.CreateCastMemberRequest;
import br.com.sidroniolima.admin.infrastructure.castmember.models.UpdateCastMemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "cast_members")
@Tag(name = "Cast Members")
public interface CastMemberAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new cast member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> create(@RequestBody CreateCastMemberRequest input);

    Pagination<CastMemberListResponse>

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a cast member by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cast member retrieved"),
            @ApiResponse(responseCode = "404", description = "Cast member was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    CastMemberResponse getById(@PathVariable String id);

    @PutMapping(value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a cast member by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cast member updated"),
            @ApiResponse(responseCode = "404", description = "Cast member was not found"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> updateById(@PathVariable String id, @RequestBody UpdateCastMemberRequest aBody);

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Delete a cast member by it's identifier")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cast member deleted"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    void deleteById(@PathVariable String id);
}
