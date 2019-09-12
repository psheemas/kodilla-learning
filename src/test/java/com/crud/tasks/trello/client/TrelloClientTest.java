package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.config.TrelloConfig;
import javafx.beans.binding.When;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init(){
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUsername()).thenReturn("przemyslawkulakowski");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException{
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/przemyslawkulakowski/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com"
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException{
        //Given
        URI uri = new URI("http://test.com/members/przemyslawkulakowski/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(null);

        //When

        //Then
        assertEquals(0, trelloClient.getTrelloBoards().size());
    }

    @Test
    public void trelloMapToBoardsTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("test_id","test_board",new ArrayList<>());
        List<TrelloBoardDto> trelloBoardsList = new ArrayList<TrelloBoardDto>();
        trelloBoardsList.add(trelloBoardDto);
        //When
        TrelloBoard trelloBoard = trelloMapper.mapToBoards(trelloBoardsList).get(0);
        //Then
        assertEquals(trelloBoardDto.getId(), trelloBoard.getId());
        assertEquals(trelloBoardDto.getName(), trelloBoard.getName());
        assertEquals(trelloBoardDto.getLists(),trelloBoard.getLists());
    }

    @Test
    public void trelloMapToBoardsDtoTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloBoard trelloBoard = new TrelloBoard("test_id","test_board",new ArrayList<>());
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard);
        //When
        TrelloBoardDto trelloBoardDto = trelloMapper.mapToBoardsDto(trelloBoardList).get(0);
        //Then
        assertEquals(trelloBoard.getId(),trelloBoardDto.getId());
        assertEquals(trelloBoard.getLists(),trelloBoardDto.getLists());
        assertEquals(trelloBoard.getName(),trelloBoardDto.getName());
    }

    @Test
    public void trelloMapToListTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloListDto trelloListDto = new TrelloListDto("test_id","test_list",true);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto);
        //When
        TrelloList trelloList = trelloMapper.mapToList(trelloListDtoList).get(0);
        //Then
        assertEquals(trelloListDto.getId(), trelloList.getId());
        assertEquals(trelloListDto.getName(), trelloList.getName());
        assertEquals(trelloListDto.isClosed(),trelloList.isClosed());
    }

    @Test
    public void trelloMapToListDtoTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloList trelloList = new TrelloList("test_id","test_list",true);
        List<TrelloList> trelloListList = new ArrayList<>();
        trelloListList.add(trelloList);
        //When
        TrelloListDto trelloListDto = trelloMapper.mapToListDto(trelloListList).get(0);
        //Then
        assertEquals(trelloList.getId(),trelloListDto.getId());
        assertEquals(trelloList.getName(),trelloListDto.getName());
        assertEquals(trelloList.isClosed(),trelloListDto.isClosed());

    }

    @Test
    public void trelloMapToBoardTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("test_id","test_list",new ArrayList<>());
        //When
        TrelloBoard trelloBoard = trelloMapper.mapToBoard(trelloBoardDto);
        //Then
        assertEquals(trelloBoardDto.getId(),trelloBoard.getId());
        assertEquals(trelloBoardDto.getName(),trelloBoard.getName());
        assertEquals(trelloBoardDto.getLists(),trelloBoard.getLists());
    }

    @Test
    public void trelloMapToCardDtoTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloCard trelloCard = new TrelloCard("test_id", "test_list","test_pos","test_listId");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(trelloCard.getDescription(),trelloCardDto.getDescription());
        assertEquals(trelloCard.getListId(),trelloCardDto.getListId());
        assertEquals(trelloCard.getName(),trelloCardDto.getName());
        assertEquals(trelloCard.getPos(),trelloCardDto.getPos());
    }

    @Test
    public void trelloMapToCardTest(){
        //Given
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_id", "test_list","test_pos","test_listId");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(trelloCardDto.getDescription(),trelloCard.getDescription());
        assertEquals(trelloCardDto.getListId(),trelloCard.getListId());
        assertEquals(trelloCardDto.getName(),trelloCard.getName());
        assertEquals(trelloCardDto.getPos(),trelloCard.getPos());
    }

    @Test
    public void taskMapToTask(){
        //Given
        TaskMapper taskMapper = new TaskMapper();
        TaskDto taskDto = new TaskDto(12313L,"title","content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(taskDto.getId(),task.getId());
        assertEquals(taskDto.getContent(), task.getContent());
        assertEquals(taskDto.getTitle(),task.getTitle());
    }

    @Test
    public void taskMapToTaskDto(){
        //Given
        TaskMapper taskMapper = new TaskMapper();
        Task task = new Task(12313L,"title","content");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(task.getContent(),taskDto.getContent());
        assertEquals(task.getId(),taskDto.getId());
        assertEquals(task.getTitle(),taskDto.getTitle());
    }

    @Test
    public void taskmapToTaskDtoList(){
        //Given
        TaskMapper taskMapper = new TaskMapper();
        List<Task> taskList = new ArrayList<>();
        Task task = new Task(12313L,"title","content");
        taskList.add(task);
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(taskList.get(0).getTitle(),taskDtoList.get(0).getTitle());
        assertEquals(taskList.get(0).getId(),taskDtoList.get(0).getId());
        assertEquals(taskList.get(0).getContent(),taskDtoList.get(0).getContent());
    }
}