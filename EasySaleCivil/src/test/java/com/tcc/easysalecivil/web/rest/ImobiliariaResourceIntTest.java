package com.tcc.easysalecivil.web.rest;

import com.tcc.easysalecivil.EasySaleCivilApp;
import com.tcc.easysalecivil.domain.Imobiliaria;
import com.tcc.easysalecivil.repository.ImobiliariaRepository;
import com.tcc.easysalecivil.service.ImobiliariaService;
import com.tcc.easysalecivil.repository.search.ImobiliariaSearchRepository;

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
 * Test class for the ImobiliariaResource REST controller.
 *
 * @see ImobiliariaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EasySaleCivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class ImobiliariaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_ENDERECO = "AAAAA";
    private static final String UPDATED_ENDERECO = "BBBBB";
    private static final String DEFAULT_TELEFONE = "AAAAA";
    private static final String UPDATED_TELEFONE = "BBBBB";
    private static final String DEFAULT_NOME_RESPONSAVEL = "AAAAA";
    private static final String UPDATED_NOME_RESPONSAVEL = "BBBBB";
    private static final String DEFAULT_CRECI_RESPONSAVEL = "AAAAA";
    private static final String UPDATED_CRECI_RESPONSAVEL = "BBBBB";

    private static final Boolean DEFAULT_IND_ATIVA = false;
    private static final Boolean UPDATED_IND_ATIVA = true;

    @Inject
    private ImobiliariaRepository imobiliariaRepository;

    @Inject
    private ImobiliariaService imobiliariaService;

    @Inject
    private ImobiliariaSearchRepository imobiliariaSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restImobiliariaMockMvc;

    private Imobiliaria imobiliaria;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImobiliariaResource imobiliariaResource = new ImobiliariaResource();
        ReflectionTestUtils.setField(imobiliariaResource, "imobiliariaService", imobiliariaService);
        this.restImobiliariaMockMvc = MockMvcBuilders.standaloneSetup(imobiliariaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        imobiliariaSearchRepository.deleteAll();
        imobiliaria = new Imobiliaria();
        imobiliaria.setNome(DEFAULT_NOME);
        imobiliaria.setEndereco(DEFAULT_ENDERECO);
        imobiliaria.setTelefone(DEFAULT_TELEFONE);
        imobiliaria.setNomeResponsavel(DEFAULT_NOME_RESPONSAVEL);
        imobiliaria.setCreciResponsavel(DEFAULT_CRECI_RESPONSAVEL);
        imobiliaria.setIndAtiva(DEFAULT_IND_ATIVA);
    }

    @Test
    @Transactional
    public void createImobiliaria() throws Exception {
        int databaseSizeBeforeCreate = imobiliariaRepository.findAll().size();

        // Create the Imobiliaria

        restImobiliariaMockMvc.perform(post("/api/imobiliarias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imobiliaria)))
                .andExpect(status().isCreated());

        // Validate the Imobiliaria in the database
        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeCreate + 1);
        Imobiliaria testImobiliaria = imobiliarias.get(imobiliarias.size() - 1);
        assertThat(testImobiliaria.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testImobiliaria.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testImobiliaria.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testImobiliaria.getNomeResponsavel()).isEqualTo(DEFAULT_NOME_RESPONSAVEL);
        assertThat(testImobiliaria.getCreciResponsavel()).isEqualTo(DEFAULT_CRECI_RESPONSAVEL);
        assertThat(testImobiliaria.isIndAtiva()).isEqualTo(DEFAULT_IND_ATIVA);

        // Validate the Imobiliaria in ElasticSearch
        Imobiliaria imobiliariaEs = imobiliariaSearchRepository.findOne(testImobiliaria.getId());
        assertThat(imobiliariaEs).isEqualToComparingFieldByField(testImobiliaria);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = imobiliariaRepository.findAll().size();
        // set the field null
        imobiliaria.setNome(null);

        // Create the Imobiliaria, which fails.

        restImobiliariaMockMvc.perform(post("/api/imobiliarias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imobiliaria)))
                .andExpect(status().isBadRequest());

        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnderecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = imobiliariaRepository.findAll().size();
        // set the field null
        imobiliaria.setEndereco(null);

        // Create the Imobiliaria, which fails.

        restImobiliariaMockMvc.perform(post("/api/imobiliarias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imobiliaria)))
                .andExpect(status().isBadRequest());

        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = imobiliariaRepository.findAll().size();
        // set the field null
        imobiliaria.setTelefone(null);

        // Create the Imobiliaria, which fails.

        restImobiliariaMockMvc.perform(post("/api/imobiliarias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imobiliaria)))
                .andExpect(status().isBadRequest());

        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = imobiliariaRepository.findAll().size();
        // set the field null
        imobiliaria.setNomeResponsavel(null);

        // Create the Imobiliaria, which fails.

        restImobiliariaMockMvc.perform(post("/api/imobiliarias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imobiliaria)))
                .andExpect(status().isBadRequest());

        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreciResponsavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = imobiliariaRepository.findAll().size();
        // set the field null
        imobiliaria.setCreciResponsavel(null);

        // Create the Imobiliaria, which fails.

        restImobiliariaMockMvc.perform(post("/api/imobiliarias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imobiliaria)))
                .andExpect(status().isBadRequest());

        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndAtivaIsRequired() throws Exception {
        int databaseSizeBeforeTest = imobiliariaRepository.findAll().size();
        // set the field null
        imobiliaria.setIndAtiva(null);

        // Create the Imobiliaria, which fails.

        restImobiliariaMockMvc.perform(post("/api/imobiliarias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imobiliaria)))
                .andExpect(status().isBadRequest());

        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImobiliarias() throws Exception {
        // Initialize the database
        imobiliariaRepository.saveAndFlush(imobiliaria);

        // Get all the imobiliarias
        restImobiliariaMockMvc.perform(get("/api/imobiliarias?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(imobiliaria.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
                .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
                .andExpect(jsonPath("$.[*].nomeResponsavel").value(hasItem(DEFAULT_NOME_RESPONSAVEL.toString())))
                .andExpect(jsonPath("$.[*].creciResponsavel").value(hasItem(DEFAULT_CRECI_RESPONSAVEL.toString())))
                .andExpect(jsonPath("$.[*].indAtiva").value(hasItem(DEFAULT_IND_ATIVA.booleanValue())));
    }

    @Test
    @Transactional
    public void getImobiliaria() throws Exception {
        // Initialize the database
        imobiliariaRepository.saveAndFlush(imobiliaria);

        // Get the imobiliaria
        restImobiliariaMockMvc.perform(get("/api/imobiliarias/{id}", imobiliaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(imobiliaria.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.nomeResponsavel").value(DEFAULT_NOME_RESPONSAVEL.toString()))
            .andExpect(jsonPath("$.creciResponsavel").value(DEFAULT_CRECI_RESPONSAVEL.toString()))
            .andExpect(jsonPath("$.indAtiva").value(DEFAULT_IND_ATIVA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImobiliaria() throws Exception {
        // Get the imobiliaria
        restImobiliariaMockMvc.perform(get("/api/imobiliarias/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImobiliaria() throws Exception {
        // Initialize the database
        imobiliariaService.save(imobiliaria);

        int databaseSizeBeforeUpdate = imobiliariaRepository.findAll().size();

        // Update the imobiliaria
        Imobiliaria updatedImobiliaria = new Imobiliaria();
        updatedImobiliaria.setId(imobiliaria.getId());
        updatedImobiliaria.setNome(UPDATED_NOME);
        updatedImobiliaria.setEndereco(UPDATED_ENDERECO);
        updatedImobiliaria.setTelefone(UPDATED_TELEFONE);
        updatedImobiliaria.setNomeResponsavel(UPDATED_NOME_RESPONSAVEL);
        updatedImobiliaria.setCreciResponsavel(UPDATED_CRECI_RESPONSAVEL);
        updatedImobiliaria.setIndAtiva(UPDATED_IND_ATIVA);

        restImobiliariaMockMvc.perform(put("/api/imobiliarias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedImobiliaria)))
                .andExpect(status().isOk());

        // Validate the Imobiliaria in the database
        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeUpdate);
        Imobiliaria testImobiliaria = imobiliarias.get(imobiliarias.size() - 1);
        assertThat(testImobiliaria.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testImobiliaria.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testImobiliaria.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testImobiliaria.getNomeResponsavel()).isEqualTo(UPDATED_NOME_RESPONSAVEL);
        assertThat(testImobiliaria.getCreciResponsavel()).isEqualTo(UPDATED_CRECI_RESPONSAVEL);
        assertThat(testImobiliaria.isIndAtiva()).isEqualTo(UPDATED_IND_ATIVA);

        // Validate the Imobiliaria in ElasticSearch
        Imobiliaria imobiliariaEs = imobiliariaSearchRepository.findOne(testImobiliaria.getId());
        assertThat(imobiliariaEs).isEqualToComparingFieldByField(testImobiliaria);
    }

    @Test
    @Transactional
    public void deleteImobiliaria() throws Exception {
        // Initialize the database
        imobiliariaService.save(imobiliaria);

        int databaseSizeBeforeDelete = imobiliariaRepository.findAll().size();

        // Get the imobiliaria
        restImobiliariaMockMvc.perform(delete("/api/imobiliarias/{id}", imobiliaria.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean imobiliariaExistsInEs = imobiliariaSearchRepository.exists(imobiliaria.getId());
        assertThat(imobiliariaExistsInEs).isFalse();

        // Validate the database is empty
        List<Imobiliaria> imobiliarias = imobiliariaRepository.findAll();
        assertThat(imobiliarias).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchImobiliaria() throws Exception {
        // Initialize the database
        imobiliariaService.save(imobiliaria);

        // Search the imobiliaria
        restImobiliariaMockMvc.perform(get("/api/_search/imobiliarias?query=id:" + imobiliaria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imobiliaria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].nomeResponsavel").value(hasItem(DEFAULT_NOME_RESPONSAVEL.toString())))
            .andExpect(jsonPath("$.[*].creciResponsavel").value(hasItem(DEFAULT_CRECI_RESPONSAVEL.toString())))
            .andExpect(jsonPath("$.[*].indAtiva").value(hasItem(DEFAULT_IND_ATIVA.booleanValue())));
    }
}
