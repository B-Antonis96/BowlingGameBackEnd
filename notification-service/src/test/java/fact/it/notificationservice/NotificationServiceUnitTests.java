package fact.it.notificationservice;

import fact.it.notificationservice.dto.NotificationRequest;
import fact.it.notificationservice.dto.NotificationResponse;
import fact.it.notificationservice.model.Notification;
import fact.it.notificationservice.repository.NotificationRepository;
import fact.it.notificationservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceUnitTests {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private Notification testNotification;
    private NotificationRequest testRequest;
    private static final String TEST_ID = "1";
    private static final String TEST_MESSAGE = "Test Message";

    @BeforeEach
    void setUp() {
        // Initialize test data
        LocalDateTime now = LocalDateTime.now();
        testNotification = Notification.builder()
                .id(TEST_ID)
                .message(TEST_MESSAGE)
                .createdAt(now)
                .updatedAt(now)
                .build();

        testRequest = NotificationRequest.builder()
                .message(TEST_MESSAGE)
                .build();
    }

    //region Create
    @Test
    public void testCreateNotification_Success() {
        // Arrange
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        // Act
        NotificationResponse response = notificationService.createNotification(testRequest);

        // Assert
        assertNotNull(response);
        assertEquals(TEST_ID, response.getId());
        assertEquals(TEST_MESSAGE, response.getMessage());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
    //endregion

    //region Read
    @Test
    public void testGetNotificationById_Success() {
        // Arrange
        when(notificationRepository.findById(TEST_ID)).thenReturn(Optional.of(testNotification));

        // Act
        NotificationResponse response = notificationService.getNotification(TEST_ID);

        // Assert
        assertNotNull(response);
        assertEquals(TEST_ID, response.getId());
        assertEquals(TEST_MESSAGE, response.getMessage());
        verify(notificationRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void testGetNotification_FailureIfNotFound() {
        // Arrange
        when(notificationRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class,
                () -> notificationService.getNotification(TEST_ID));
        verify(notificationRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void testGetAllNotifications_Success() {
        // Arrange
        List<Notification> notifications = Arrays.asList(
                testNotification,
                testNotification.toBuilder().id("2").build()
        );
        when(notificationRepository.findAll()).thenReturn(notifications);

        // Act
        List<NotificationResponse> responses = notificationService.getAllNotifications();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        verify(notificationRepository, times(1)).findAll();
    }
    //endregion

    //region Update
    @Test
    public void testUpdateNotification_Success() {
        // Arrange
        Notification updatedNotification = testNotification.toBuilder()
                .message("Updated Message")
                .build();
        when(notificationRepository.findById(TEST_ID)).thenReturn(Optional.of(testNotification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(updatedNotification);

        NotificationRequest updateRequest = NotificationRequest.builder()
                .message("Updated Message")
                .build();

        // Act
        NotificationResponse response = notificationService.updateNotification(TEST_ID, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Message", response.getMessage());
        verify(notificationRepository, times(1)).findById(TEST_ID);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void testUpdateNotification_FailureIfNotFound() {
        // Arrange
        when(notificationRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        NotificationRequest updateRequest = NotificationRequest.builder()
                .message("Updated Message")
                .build();

        // Act & Assert
        assertThrows(ResponseStatusException.class,
                () -> notificationService.updateNotification(TEST_ID, updateRequest));

        // Verify that findById was called but save was never called
        verify(notificationRepository, times(1)).findById(TEST_ID);
        verify(notificationRepository, never()).save(any(Notification.class));
    }
    //endregion

    //region Delete
    @Test
    public void testDeleteNotification_Success() {
        // Arrange
        when(notificationRepository.existsById(TEST_ID)).thenReturn(true);
        doNothing().when(notificationRepository).deleteById(TEST_ID);

        // Act & Assert
        assertDoesNotThrow(() -> notificationService.deleteNotification(TEST_ID));
        verify(notificationRepository, times(1)).existsById(TEST_ID);
        verify(notificationRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    public void testDeleteNotification_FailureIfNotFound() {
        // Arrange
        when(notificationRepository.existsById(TEST_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class,
                () -> notificationService.deleteNotification(TEST_ID));
        verify(notificationRepository, times(1)).existsById(TEST_ID);
        verify(notificationRepository, never()).deleteById(any());
    }
    //endregion
}