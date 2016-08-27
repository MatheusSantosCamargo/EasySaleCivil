package com.tcc.easysalecivil.web.rest;

import com.tcc.easysalecivil.EasySaleCivilApp;
import com.tcc.easysalecivil.domain.CadastroCliente;
import com.tcc.easysalecivil.repository.CadastroClienteRepository;
import com.tcc.easysalecivil.service.CadastroClienteService;
import com.tcc.easysalecivil.repository.search.CadastroClienteSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CadastroClienteResource REST controller.
 *
 * @see CadastroClienteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EasySaleCivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class CadastroClienteResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_CPF_CPNJ = "AAAAAAAAAAA";
    private static final String UPDATED_CPF_CPNJ = "BBBBBBBBBBB";
    private static final String DEFAULT_RG = "AAAAA";
    private static final String UPDATED_RG = "BBBBB";
    private static final String DEFAULT_RUA = "AAAAA";
    private static final String UPDATED_RUA = "BBBBB";
    private static final String DEFAULT_BAIRRO = "AAAAA";
    private static final String UPDATED_BAIRRO = "BBBBB";
    private static final String DEFAULT_NUMERO = "AAAAA";
    private static final String UPDATED_NUMERO = "BBBBB";
    private static final String DEFAULT_COMPLEMENTO = "AAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBB";
    private static final String DEFAULT_CIDADE = "AAAAA";
    private static final String UPDATED_CIDADE = "BBBBB";
    private static final String DEFAULT_CEP = "AAAAA";
    private static final String UPDATED_CEP = "BBBBB";
    private static final String DEFAULT_ESTADO = "AAAAA";
    private static final String UPDATED_ESTADO = "BBBBB";
    private static final String DEFAULT_TELEFONE = "AAAAA";
    private static final String UPDATED_TELEFONE = "BBBBB";
    private static final String DEFAULT_CELULAR = "AAAAA";
    private static final String UPDATED_CELULAR = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private CadastroClienteRepository cadastroClienteRepository;

    @Inject
    private CadastroClienteService cadastroClienteService;

    @Inject
    private CadastroClienteSearchRepository cadastroClienteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCadastroClienteMockMvc;

    private CadastroCliente cadastroCliente;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CadastroClienteResource cadastroClienteResource = new CadastroClienteResource();
        ReflectionTestUtils.setField(cadastroClienteResource, "cadastroClienteService", cadastroClienteService);
        this.restCadastroClienteMockMvc = MockMvcBuilders.standaloneSetup(cadastroClienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cadastroClienteSearchRepository.deleteAll();
        cadastroCliente = new CadastroCliente();
        cadastroCliente.setNome(DEFAULT_NOME);
        cadastroCliente.setCpf_cpnj(DEFAULT_CPF_CPNJ);
        cadastroCliente.setRg(DEFAULT_RG);
        cadastroCliente.setRua(DEFAULT_RUA);
        cadastroCliente.setBairro(DEFAULT_BAIRRO);
        cadastroCliente.setNumero(DEFAULT_NUMERO);
        cadastroCliente.setComplemento(DEFAULT_COMPLEMENTO);
        cadastroCliente.setCidade(DEFAULT_CIDADE);
        cadastroCliente.setCep(DEFAULT_CEP);
        cadastroCliente.setEstado(DEFAULT_ESTADO);
        cadastroCliente.setTelefone(DEFAULT_TELEFONE);
        cadastroCliente.setCelular(DEFAULT_CELULAR);
        cadastroCliente.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createCadastroCliente() throws Exception {
        int databaseSizeBeforeCreate = cadastroClienteRepository.findAll().size();

        // Create the CadastroCliente

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isCreated());

        // Validate the CadastroCliente in the database
        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeCreate + 1);
        CadastroCliente testCadastroCliente = cadastroClientes.get(cadastroClientes.size() - 1);
        assertThat(testCadastroCliente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCadastroCliente.getCpf_cpnj()).isEqualTo(DEFAULT_CPF_CPNJ);
        assertThat(testCadastroCliente.getRg()).isEqualTo(DEFAULT_RG);
        assertThat(testCadastroCliente.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testCadastroCliente.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testCadastroCliente.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCadastroCliente.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testCadastroCliente.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCadastroCliente.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testCadastroCliente.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCadastroCliente.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testCadastroCliente.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testCadastroCliente.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the CadastroCliente in ElasticSearch
        CadastroCliente cadastroClienteEs = cadastroClienteSearchRepository.findOne(testCadastroCliente.getId());
        assertThat(cadastroClienteEs).isEqualToComparingFieldByField(testCadastroCliente);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setNome(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCpf_cpnjIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setCpf_cpnj(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setRua(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setBairro(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setNumero(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setCidade(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setCep(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setEstado(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = cadastroClienteRepository.findAll().size();
        // set the field null
        cadastroCliente.setEmail(null);

        // Create the CadastroCliente, which fails.

        restCadastroClienteMockMvc.perform(post("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cadastroCliente)))
                .andExpect(status().isBadRequest());

        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCadastroClientes() throws Exception {
        // Initialize the database
        cadastroClienteRepository.saveAndFlush(cadastroCliente);

        // Get all the cadastroClientes
        restCadastroClienteMockMvc.perform(get("/api/cadastro-clientes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cadastroCliente.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].cpf_cpnj").value(hasItem(DEFAULT_CPF_CPNJ.toString())))
                .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG.toString())))
                .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA.toString())))
                .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
                .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
                .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())))
                .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
                .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
                .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
                .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
                .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getCadastroCliente() throws Exception {
        // Initialize the database
        cadastroClienteRepository.saveAndFlush(cadastroCliente);

        // Get the cadastroCliente
        restCadastroClienteMockMvc.perform(get("/api/cadastro-clientes/{id}", cadastroCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cadastroCliente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cpf_cpnj").value(DEFAULT_CPF_CPNJ.toString()))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG.toString()))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA.toString()))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO.toString()))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCadastroCliente() throws Exception {
        // Get the cadastroCliente
        restCadastroClienteMockMvc.perform(get("/api/cadastro-clientes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCadastroCliente() throws Exception {
        // Initialize the database
        cadastroClienteService.save(cadastroCliente);

        int databaseSizeBeforeUpdate = cadastroClienteRepository.findAll().size();

        // Update the cadastroCliente
        CadastroCliente updatedCadastroCliente = new CadastroCliente();
        updatedCadastroCliente.setId(cadastroCliente.getId());
        updatedCadastroCliente.setNome(UPDATED_NOME);
        updatedCadastroCliente.setCpf_cpnj(UPDATED_CPF_CPNJ);
        updatedCadastroCliente.setRg(UPDATED_RG);
        updatedCadastroCliente.setRua(UPDATED_RUA);
        updatedCadastroCliente.setBairro(UPDATED_BAIRRO);
        updatedCadastroCliente.setNumero(UPDATED_NUMERO);
        updatedCadastroCliente.setComplemento(UPDATED_COMPLEMENTO);
        updatedCadastroCliente.setCidade(UPDATED_CIDADE);
        updatedCadastroCliente.setCep(UPDATED_CEP);
        updatedCadastroCliente.setEstado(UPDATED_ESTADO);
        updatedCadastroCliente.setTelefone(UPDATED_TELEFONE);
        updatedCadastroCliente.setCelular(UPDATED_CELULAR);
        updatedCadastroCliente.setEmail(UPDATED_EMAIL);

        restCadastroClienteMockMvc.perform(put("/api/cadastro-clientes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCadastroCliente)))
                .andExpect(status().isOk());

        // Validate the CadastroCliente in the database
        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeUpdate);
        CadastroCliente testCadastroCliente = cadastroClientes.get(cadastroClientes.size() - 1);
        assertThat(testCadastroCliente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCadastroCliente.getCpf_cpnj()).isEqualTo(UPDATED_CPF_CPNJ);
        assertThat(testCadastroCliente.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testCadastroCliente.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testCadastroCliente.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testCadastroCliente.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCadastroCliente.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testCadastroCliente.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCadastroCliente.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testCadastroCliente.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCadastroCliente.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testCadastroCliente.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testCadastroCliente.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the CadastroCliente in ElasticSearch
        CadastroCliente cadastroClienteEs = cadastroClienteSearchRepository.findOne(testCadastroCliente.getId());
        assertThat(cadastroClienteEs).isEqualToComparingFieldByField(testCadastroCliente);
    }

    @Test
    @Transactional
    public void deleteCadastroCliente() throws Exception {
        // Initialize the database
        cadastroClienteService.save(cadastroCliente);

        int databaseSizeBeforeDelete = cadastroClienteRepository.findAll().size();

        // Get the cadastroCliente
        restCadastroClienteMockMvc.perform(delete("/api/cadastro-clientes/{id}", cadastroCliente.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean cadastroClienteExistsInEs = cadastroClienteSearchRepository.exists(cadastroCliente.getId());
        assertThat(cadastroClienteExistsInEs).isFalse();

        // Validate the database is empty
        List<CadastroCliente> cadastroClientes = cadastroClienteRepository.findAll();
        assertThat(cadastroClientes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCadastroCliente() throws Exception {
        // Initialize the database
        cadastroClienteService.save(cadastroCliente);

        // Search the cadastroCliente
        restCadastroClienteMockMvc.perform(get("/api/_search/cadastro-clientes?query=id:" + cadastroCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cadastroCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cpf_cpnj").value(hasItem(DEFAULT_CPF_CPNJ.toString())))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG.toString())))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA.toString())))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
}
