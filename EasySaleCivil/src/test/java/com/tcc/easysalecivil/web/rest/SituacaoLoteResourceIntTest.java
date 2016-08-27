package com.tcc.easysalecivil.web.rest;

import com.tcc.easysalecivil.EasySaleCivilApp;
import com.tcc.easysalecivil.domain.SituacaoLote;
import com.tcc.easysalecivil.repository.SituacaoLoteRepository;
import com.tcc.easysalecivil.service.SituacaoLoteService;
import com.tcc.easysalecivil.repository.search.SituacaoLoteSearchRepository;

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
 * Test class for the SituacaoLoteResource REST controller.
 *
 * @see SituacaoLoteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EasySaleCivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class SituacaoLoteResourceIntTest {

    private static final String DEFAULT_SITUACAO = "AAAAA";
    private static final String UPDATED_SITUACAO = "BBBBB";
    private static final String DEFAULT_COR = "AAAAA";
    private static final String UPDATED_COR = "BBBBB";

    @Inject
    private SituacaoLoteRepository situacaoLoteRepository;

    @Inject
    private SituacaoLoteService situacaoLoteService;

    @Inject
    private SituacaoLoteSearchRepository situacaoLoteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSituacaoLoteMockMvc;

    private SituacaoLote situacaoLote;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SituacaoLoteResource situacaoLoteResource = new SituacaoLoteResource();
        ReflectionTestUtils.setField(situacaoLoteResource, "situacaoLoteService", situacaoLoteService);
        this.restSituacaoLoteMockMvc = MockMvcBuilders.standaloneSetup(situacaoLoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        situacaoLoteSearchRepository.deleteAll();
        situacaoLote = new SituacaoLote();
        situacaoLote.setSituacao(DEFAULT_SITUACAO);
        situacaoLote.setCor(DEFAULT_COR);
    }

    @Test
    @Transactional
    public void createSituacaoLote() throws Exception {
        int databaseSizeBeforeCreate = situacaoLoteRepository.findAll().size();

        // Create the SituacaoLote

        restSituacaoLoteMockMvc.perform(post("/api/situacao-lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(situacaoLote)))
                .andExpect(status().isCreated());

        // Validate the SituacaoLote in the database
        List<SituacaoLote> situacaoLotes = situacaoLoteRepository.findAll();
        assertThat(situacaoLotes).hasSize(databaseSizeBeforeCreate + 1);
        SituacaoLote testSituacaoLote = situacaoLotes.get(situacaoLotes.size() - 1);
        assertThat(testSituacaoLote.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
        assertThat(testSituacaoLote.getCor()).isEqualTo(DEFAULT_COR);

        // Validate the SituacaoLote in ElasticSearch
        SituacaoLote situacaoLoteEs = situacaoLoteSearchRepository.findOne(testSituacaoLote.getId());
        assertThat(situacaoLoteEs).isEqualToComparingFieldByField(testSituacaoLote);
    }

    @Test
    @Transactional
    public void checkSituacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = situacaoLoteRepository.findAll().size();
        // set the field null
        situacaoLote.setSituacao(null);

        // Create the SituacaoLote, which fails.

        restSituacaoLoteMockMvc.perform(post("/api/situacao-lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(situacaoLote)))
                .andExpect(status().isBadRequest());

        List<SituacaoLote> situacaoLotes = situacaoLoteRepository.findAll();
        assertThat(situacaoLotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorIsRequired() throws Exception {
        int databaseSizeBeforeTest = situacaoLoteRepository.findAll().size();
        // set the field null
        situacaoLote.setCor(null);

        // Create the SituacaoLote, which fails.

        restSituacaoLoteMockMvc.perform(post("/api/situacao-lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(situacaoLote)))
                .andExpect(status().isBadRequest());

        List<SituacaoLote> situacaoLotes = situacaoLoteRepository.findAll();
        assertThat(situacaoLotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSituacaoLotes() throws Exception {
        // Initialize the database
        situacaoLoteRepository.saveAndFlush(situacaoLote);

        // Get all the situacaoLotes
        restSituacaoLoteMockMvc.perform(get("/api/situacao-lotes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(situacaoLote.getId().intValue())))
                .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
                .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }

    @Test
    @Transactional
    public void getSituacaoLote() throws Exception {
        // Initialize the database
        situacaoLoteRepository.saveAndFlush(situacaoLote);

        // Get the situacaoLote
        restSituacaoLoteMockMvc.perform(get("/api/situacao-lotes/{id}", situacaoLote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(situacaoLote.getId().intValue()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSituacaoLote() throws Exception {
        // Get the situacaoLote
        restSituacaoLoteMockMvc.perform(get("/api/situacao-lotes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSituacaoLote() throws Exception {
        // Initialize the database
        situacaoLoteService.save(situacaoLote);

        int databaseSizeBeforeUpdate = situacaoLoteRepository.findAll().size();

        // Update the situacaoLote
        SituacaoLote updatedSituacaoLote = new SituacaoLote();
        updatedSituacaoLote.setId(situacaoLote.getId());
        updatedSituacaoLote.setSituacao(UPDATED_SITUACAO);
        updatedSituacaoLote.setCor(UPDATED_COR);

        restSituacaoLoteMockMvc.perform(put("/api/situacao-lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSituacaoLote)))
                .andExpect(status().isOk());

        // Validate the SituacaoLote in the database
        List<SituacaoLote> situacaoLotes = situacaoLoteRepository.findAll();
        assertThat(situacaoLotes).hasSize(databaseSizeBeforeUpdate);
        SituacaoLote testSituacaoLote = situacaoLotes.get(situacaoLotes.size() - 1);
        assertThat(testSituacaoLote.getSituacao()).isEqualTo(UPDATED_SITUACAO);
        assertThat(testSituacaoLote.getCor()).isEqualTo(UPDATED_COR);

        // Validate the SituacaoLote in ElasticSearch
        SituacaoLote situacaoLoteEs = situacaoLoteSearchRepository.findOne(testSituacaoLote.getId());
        assertThat(situacaoLoteEs).isEqualToComparingFieldByField(testSituacaoLote);
    }

    @Test
    @Transactional
    public void deleteSituacaoLote() throws Exception {
        // Initialize the database
        situacaoLoteService.save(situacaoLote);

        int databaseSizeBeforeDelete = situacaoLoteRepository.findAll().size();

        // Get the situacaoLote
        restSituacaoLoteMockMvc.perform(delete("/api/situacao-lotes/{id}", situacaoLote.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean situacaoLoteExistsInEs = situacaoLoteSearchRepository.exists(situacaoLote.getId());
        assertThat(situacaoLoteExistsInEs).isFalse();

        // Validate the database is empty
        List<SituacaoLote> situacaoLotes = situacaoLoteRepository.findAll();
        assertThat(situacaoLotes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSituacaoLote() throws Exception {
        // Initialize the database
        situacaoLoteService.save(situacaoLote);

        // Search the situacaoLote
        restSituacaoLoteMockMvc.perform(get("/api/_search/situacao-lotes?query=id:" + situacaoLote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situacaoLote.getId().intValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR.toString())));
    }
}
