package com.tcc.easysalecivil.web.rest;

import com.tcc.easysalecivil.EasySaleCivilApp;
import com.tcc.easysalecivil.domain.Corretor;
import com.tcc.easysalecivil.repository.CorretorRepository;
import com.tcc.easysalecivil.service.CorretorService;
import com.tcc.easysalecivil.repository.search.CorretorSearchRepository;

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
 * Test class for the CorretorResource REST controller.
 *
 * @see CorretorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EasySaleCivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class CorretorResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_CRECI = "AAAAA";
    private static final String UPDATED_CRECI = "BBBBB";
    private static final String DEFAULT_PRIMEIRO_TELEFONE = "AAAAA";
    private static final String UPDATED_PRIMEIRO_TELEFONE = "BBBBB";
    private static final String DEFAULT_SEGUNDO_TELEFONE = "AAAAA";
    private static final String UPDATED_SEGUNDO_TELEFONE = "BBBBB";
    private static final String DEFAULT_PRIMEIRO_EMAIL = "AAAAA";
    private static final String UPDATED_PRIMEIRO_EMAIL = "BBBBB";
    private static final String DEFAULT_SEGUNDO_EMAIL = "AAAAA";
    private static final String UPDATED_SEGUNDO_EMAIL = "BBBBB";

    @Inject
    private CorretorRepository corretorRepository;

    @Inject
    private CorretorService corretorService;

    @Inject
    private CorretorSearchRepository corretorSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCorretorMockMvc;

    private Corretor corretor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CorretorResource corretorResource = new CorretorResource();
        ReflectionTestUtils.setField(corretorResource, "corretorService", corretorService);
        this.restCorretorMockMvc = MockMvcBuilders.standaloneSetup(corretorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        corretorSearchRepository.deleteAll();
        corretor = new Corretor();
        corretor.setNome(DEFAULT_NOME);
        corretor.setCreci(DEFAULT_CRECI);
        corretor.setPrimeiroTelefone(DEFAULT_PRIMEIRO_TELEFONE);
        corretor.setSegundoTelefone(DEFAULT_SEGUNDO_TELEFONE);
        corretor.setPrimeiroEmail(DEFAULT_PRIMEIRO_EMAIL);
        corretor.setSegundoEmail(DEFAULT_SEGUNDO_EMAIL);
    }

    @Test
    @Transactional
    public void createCorretor() throws Exception {
        int databaseSizeBeforeCreate = corretorRepository.findAll().size();

        // Create the Corretor

        restCorretorMockMvc.perform(post("/api/corretors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(corretor)))
                .andExpect(status().isCreated());

        // Validate the Corretor in the database
        List<Corretor> corretors = corretorRepository.findAll();
        assertThat(corretors).hasSize(databaseSizeBeforeCreate + 1);
        Corretor testCorretor = corretors.get(corretors.size() - 1);
        assertThat(testCorretor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCorretor.getCreci()).isEqualTo(DEFAULT_CRECI);
        assertThat(testCorretor.getPrimeiroTelefone()).isEqualTo(DEFAULT_PRIMEIRO_TELEFONE);
        assertThat(testCorretor.getSegundoTelefone()).isEqualTo(DEFAULT_SEGUNDO_TELEFONE);
        assertThat(testCorretor.getPrimeiroEmail()).isEqualTo(DEFAULT_PRIMEIRO_EMAIL);
        assertThat(testCorretor.getSegundoEmail()).isEqualTo(DEFAULT_SEGUNDO_EMAIL);

        // Validate the Corretor in ElasticSearch
        Corretor corretorEs = corretorSearchRepository.findOne(testCorretor.getId());
        assertThat(corretorEs).isEqualToComparingFieldByField(testCorretor);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = corretorRepository.findAll().size();
        // set the field null
        corretor.setNome(null);

        // Create the Corretor, which fails.

        restCorretorMockMvc.perform(post("/api/corretors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(corretor)))
                .andExpect(status().isBadRequest());

        List<Corretor> corretors = corretorRepository.findAll();
        assertThat(corretors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreciIsRequired() throws Exception {
        int databaseSizeBeforeTest = corretorRepository.findAll().size();
        // set the field null
        corretor.setCreci(null);

        // Create the Corretor, which fails.

        restCorretorMockMvc.perform(post("/api/corretors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(corretor)))
                .andExpect(status().isBadRequest());

        List<Corretor> corretors = corretorRepository.findAll();
        assertThat(corretors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimeiroTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = corretorRepository.findAll().size();
        // set the field null
        corretor.setPrimeiroTelefone(null);

        // Create the Corretor, which fails.

        restCorretorMockMvc.perform(post("/api/corretors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(corretor)))
                .andExpect(status().isBadRequest());

        List<Corretor> corretors = corretorRepository.findAll();
        assertThat(corretors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrimeiroEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = corretorRepository.findAll().size();
        // set the field null
        corretor.setPrimeiroEmail(null);

        // Create the Corretor, which fails.

        restCorretorMockMvc.perform(post("/api/corretors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(corretor)))
                .andExpect(status().isBadRequest());

        List<Corretor> corretors = corretorRepository.findAll();
        assertThat(corretors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorretors() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        // Get all the corretors
        restCorretorMockMvc.perform(get("/api/corretors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(corretor.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].creci").value(hasItem(DEFAULT_CRECI.toString())))
                .andExpect(jsonPath("$.[*].primeiroTelefone").value(hasItem(DEFAULT_PRIMEIRO_TELEFONE.toString())))
                .andExpect(jsonPath("$.[*].segundoTelefone").value(hasItem(DEFAULT_SEGUNDO_TELEFONE.toString())))
                .andExpect(jsonPath("$.[*].primeiroEmail").value(hasItem(DEFAULT_PRIMEIRO_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].segundoEmail").value(hasItem(DEFAULT_SEGUNDO_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getCorretor() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        // Get the corretor
        restCorretorMockMvc.perform(get("/api/corretors/{id}", corretor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(corretor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.creci").value(DEFAULT_CRECI.toString()))
            .andExpect(jsonPath("$.primeiroTelefone").value(DEFAULT_PRIMEIRO_TELEFONE.toString()))
            .andExpect(jsonPath("$.segundoTelefone").value(DEFAULT_SEGUNDO_TELEFONE.toString()))
            .andExpect(jsonPath("$.primeiroEmail").value(DEFAULT_PRIMEIRO_EMAIL.toString()))
            .andExpect(jsonPath("$.segundoEmail").value(DEFAULT_SEGUNDO_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCorretor() throws Exception {
        // Get the corretor
        restCorretorMockMvc.perform(get("/api/corretors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorretor() throws Exception {
        // Initialize the database
        corretorService.save(corretor);

        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();

        // Update the corretor
        Corretor updatedCorretor = new Corretor();
        updatedCorretor.setId(corretor.getId());
        updatedCorretor.setNome(UPDATED_NOME);
        updatedCorretor.setCreci(UPDATED_CRECI);
        updatedCorretor.setPrimeiroTelefone(UPDATED_PRIMEIRO_TELEFONE);
        updatedCorretor.setSegundoTelefone(UPDATED_SEGUNDO_TELEFONE);
        updatedCorretor.setPrimeiroEmail(UPDATED_PRIMEIRO_EMAIL);
        updatedCorretor.setSegundoEmail(UPDATED_SEGUNDO_EMAIL);

        restCorretorMockMvc.perform(put("/api/corretors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCorretor)))
                .andExpect(status().isOk());

        // Validate the Corretor in the database
        List<Corretor> corretors = corretorRepository.findAll();
        assertThat(corretors).hasSize(databaseSizeBeforeUpdate);
        Corretor testCorretor = corretors.get(corretors.size() - 1);
        assertThat(testCorretor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCorretor.getCreci()).isEqualTo(UPDATED_CRECI);
        assertThat(testCorretor.getPrimeiroTelefone()).isEqualTo(UPDATED_PRIMEIRO_TELEFONE);
        assertThat(testCorretor.getSegundoTelefone()).isEqualTo(UPDATED_SEGUNDO_TELEFONE);
        assertThat(testCorretor.getPrimeiroEmail()).isEqualTo(UPDATED_PRIMEIRO_EMAIL);
        assertThat(testCorretor.getSegundoEmail()).isEqualTo(UPDATED_SEGUNDO_EMAIL);

        // Validate the Corretor in ElasticSearch
        Corretor corretorEs = corretorSearchRepository.findOne(testCorretor.getId());
        assertThat(corretorEs).isEqualToComparingFieldByField(testCorretor);
    }

    @Test
    @Transactional
    public void deleteCorretor() throws Exception {
        // Initialize the database
        corretorService.save(corretor);

        int databaseSizeBeforeDelete = corretorRepository.findAll().size();

        // Get the corretor
        restCorretorMockMvc.perform(delete("/api/corretors/{id}", corretor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean corretorExistsInEs = corretorSearchRepository.exists(corretor.getId());
        assertThat(corretorExistsInEs).isFalse();

        // Validate the database is empty
        List<Corretor> corretors = corretorRepository.findAll();
        assertThat(corretors).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCorretor() throws Exception {
        // Initialize the database
        corretorService.save(corretor);

        // Search the corretor
        restCorretorMockMvc.perform(get("/api/_search/corretors?query=id:" + corretor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corretor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].creci").value(hasItem(DEFAULT_CRECI.toString())))
            .andExpect(jsonPath("$.[*].primeiroTelefone").value(hasItem(DEFAULT_PRIMEIRO_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].segundoTelefone").value(hasItem(DEFAULT_SEGUNDO_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].primeiroEmail").value(hasItem(DEFAULT_PRIMEIRO_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].segundoEmail").value(hasItem(DEFAULT_SEGUNDO_EMAIL.toString())));
    }
}
