package com.mvp.kfz.service.implementation;

import com.mvp.kfz.data.entity.CarFeature;
import com.mvp.kfz.data.entity.UserConfiguration;
import com.mvp.kfz.data.entity.Users;
import com.mvp.kfz.model.FeatureTypes;
import com.mvp.kfz.repository.CarFeatureRepository;
import com.mvp.kfz.repository.UserConfigurationRepository;
import com.mvp.kfz.service.ContextUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ConfigurationServiceImplTest {

    @Mock
    private UserConfigurationRepository userConfigurationRepository;

    @Mock
    private CarFeatureRepository carFeatureRepository;

    @Mock
    private ContextUserService contextUserService;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenUserIdWhenHasOneUserConfigurationThenReturnOne() {
        // Given
        Users user = Users.builder().id(1L).build();
        when(contextUserService.retrieveContextUser()).thenReturn(user);

        List<UserConfiguration> configurations = new ArrayList<>();
        configurations.add(new UserConfiguration());
        when(userConfigurationRepository.findByUserId(1L)).thenReturn(configurations);

        // When
        List<UserConfiguration> result = configurationService.getConfigurations();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void givenUserIdWhenHasNoUserConfigurationsThenReturnEmptyList() {
        // Given
        Users user = Users.builder().id(1L).build();
        when(contextUserService.retrieveContextUser()).thenReturn(user);

        List<UserConfiguration> configurations = new ArrayList<>();
        when(userConfigurationRepository.findByUserId(1L)).thenReturn(configurations);

        // When
        List<UserConfiguration> result = configurationService.getConfigurations();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void whenFailsToRetrieveContextUserThenThrowsRuntimeException() {
        // Given
        when(contextUserService.retrieveContextUser()).thenThrow(new RuntimeException("Failed to retrieve user"));

        // When/Then
        assertThrows(RuntimeException.class, () -> configurationService.getConfigurations());
    }

    @Test
    public void givenUserConfigurationWhenCompleteThenSave() {
        // Given
        CarFeature carClass = CarFeature.builder().id(1L).featureType(FeatureTypes.CLASS).name("class").build();
        CarFeature carType = CarFeature.builder().id(2L).featureType(FeatureTypes.TYPE).name("type").build();
        CarFeature carMotor = CarFeature.builder().id(3L).featureType(FeatureTypes.MOTOR).name("motor").build();
        CarFeature carColor = CarFeature.builder().id(4L).featureType(FeatureTypes.COLOR).name("color").build();
        UserConfiguration newConfiguration =UserConfiguration.builder()
                .carClass(carClass)
                .carType(carType)
                .carMotor(carMotor)
                .carColor(carColor)
                .carExtras(List.of())
                .build();

        Users user = Users.builder().id(1L).build();
        when(contextUserService.retrieveContextUser()).thenReturn(user);

        when(carFeatureRepository.findById(eq(1L))).thenReturn(Optional.ofNullable(carClass));
        when(carFeatureRepository.findById(eq(2L))).thenReturn(Optional.ofNullable(carType));
        when(carFeatureRepository.findById(eq(3L))).thenReturn(Optional.ofNullable(carMotor));
        when(carFeatureRepository.findById(eq(4L))).thenReturn(Optional.ofNullable(carColor));

        when(userConfigurationRepository.save(any(UserConfiguration.class))).thenReturn(newConfiguration);

        // When
        UserConfiguration result = configurationService.addConfiguration(newConfiguration);

        // Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getUserId());
    }

    @Test
    public void givenUserConfigurationWhenWrongFeatureTypeThenValidationFailure() {
        // Given
        CarFeature carType = CarFeature.builder().id(2L).featureType(FeatureTypes.TYPE).name("type").build();
        CarFeature carMotor = CarFeature.builder().id(3L).featureType(FeatureTypes.MOTOR).name("motor").build();
        CarFeature carColor = CarFeature.builder().id(4L).featureType(FeatureTypes.COLOR).name("color").build();
        UserConfiguration newConfiguration = UserConfiguration.builder()
                .carClass(carType)
                .carType(carType)
                .carMotor(carMotor)
                .carColor(carColor)
                .carExtras(List.of())
                .build();
        Users user = Users.builder().id(1L).build();
        when(contextUserService.retrieveContextUser()).thenReturn(user);
        // Set up other necessary mocks as needed

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> configurationService.addConfiguration(newConfiguration));
        verify(userConfigurationRepository, never()).save(any(UserConfiguration.class));
    }

    @Test
    public void givenUserConfigurationWhenSaveFailsThenThrowException() {
        // Given
        UserConfiguration newConfiguration = new UserConfiguration();
        newConfiguration.setCarClass(new CarFeature()); // Set other required fields as needed
        Users users = Users.builder().id(1L).build();
        when(contextUserService.retrieveContextUser()).thenReturn(users);

        when(userConfigurationRepository.save(any(UserConfiguration.class)))
                .thenThrow(new RuntimeException("Failed to save configuration"));

        // When/Then
        assertThrows(RuntimeException.class, () -> configurationService.addConfiguration(newConfiguration));
    }

    @Test
    public void geivenUserConfigurationIdWhenExistsThenDelete() {
        // Given
        Users users = Users.builder().id(2L).build();
        Long configurationId = 1L;
        UserConfiguration userConfiguration = UserConfiguration.builder()
                .id(configurationId)
                .userId(users.getId())
                .build();
        when(userConfigurationRepository.findById(configurationId)).thenReturn(Optional.of(userConfiguration));

        when(contextUserService.retrieveContextUser()).thenReturn(users);

        // When
        configurationService.deleteConfiguration(configurationId);

        // Then
        verify(userConfigurationRepository, times(1)).deleteById(configurationId);
    }

    @Test
    public void givenUserConfigurationIdWhenWrongUserThenNotAuthorized() {
        // Given
        Users users = Users.builder().id(2L).build();
        Long configurationId = 1L;
        UserConfiguration userConfiguration = UserConfiguration.builder()
                .id(configurationId)
                .userId(3L)
                .build();
        when(userConfigurationRepository.findById(configurationId)).thenReturn(Optional.of(userConfiguration));

        when(contextUserService.retrieveContextUser()).thenReturn(users);

        // When/Then
        assertThrows(IllegalCallerException.class, () -> configurationService.deleteConfiguration(configurationId));
        verify(userConfigurationRepository, never()).deleteById(configurationId);
    }

    @Test
    public void givenUserConfigurationIdWhenDoesNotExistThenThrowException() {
        // Given
        Long configurationId = 1L;
        when(userConfigurationRepository.findById(configurationId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(Exception.class, () -> configurationService.deleteConfiguration(configurationId));
        verify(userConfigurationRepository, never()).deleteById(configurationId);
    }

    @Test
    public void givenUserConfigurationWhenCompleteThenCalculatePrice() {
        // Given
        CarFeature carClass = CarFeature.builder().id(1L).featureType(FeatureTypes.CLASS).name("class").price(1F).build();
        CarFeature carType = CarFeature.builder().id(2L).featureType(FeatureTypes.TYPE).name("type").price(10F).build();
        CarFeature carMotor = CarFeature.builder().id(3L).featureType(FeatureTypes.MOTOR).name("motor").price(100F).build();
        CarFeature carColor = CarFeature.builder().id(4L).featureType(FeatureTypes.COLOR).name("color").price(1000F).build();
        UserConfiguration newUserConfiguration = UserConfiguration.builder()
                .carClass(carClass)
                .carType(carType)
                .carMotor(carMotor)
                .carColor(carColor)
                .carExtras(List.of())
                .build();

        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.CLASS)))
                .thenReturn(Optional.of(carClass));
        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.TYPE)))
                .thenReturn(Optional.of(carType));
        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.MOTOR)))
                .thenReturn(Optional.of(carMotor));
        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.COLOR)))
                .thenReturn(Optional.of(carColor));

        // When
        UserConfiguration result = configurationService.getConfigurationPrice(newUserConfiguration);

        // Then
        assertNotNull(result);
        assertEquals(result.getPrice(),1111F);
        verify(carFeatureRepository, never()).findFirstByFeatureTypeOrderByPriceAsc(any());
        // Add assertions as needed for the calculated price and other properties
    }

    @Test
    public void givenInconmpleteUserConfigurationWhenProcessingFeaturesThenGetBasicConfiguration() {
        // Given
        CarFeature carClass = CarFeature.builder().id(1L).featureType(FeatureTypes.CLASS).name("class").price(1F).build();
        CarFeature carType = CarFeature.builder().featureType(FeatureTypes.TYPE).name("type").price(10F).build();
        CarFeature carMotor = CarFeature.builder().featureType(FeatureTypes.MOTOR).name("motor").price(100F).build();
        CarFeature carColor = CarFeature.builder().featureType(FeatureTypes.COLOR).name("color").price(1000F).build();
        UserConfiguration newUserConfiguration = UserConfiguration.builder()
                .carClass(carClass)
                .carType(carType)
                .carMotor(carMotor)
                .carColor(carColor)
                .carExtras(List.of())
                .build();

        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.CLASS)))
                .thenReturn(Optional.of(carClass));
        when(carFeatureRepository.findFirstByFeatureTypeOrderByPriceAsc(eq(FeatureTypes.TYPE)))
                .thenReturn(carType);
        when(carFeatureRepository.findFirstByFeatureTypeOrderByPriceAsc(eq(FeatureTypes.MOTOR)))
                .thenReturn(carMotor);
        when(carFeatureRepository.findFirstByFeatureTypeOrderByPriceAsc(eq(FeatureTypes.COLOR)))
                .thenReturn(carColor);

        // When
        UserConfiguration result = configurationService.getConfigurationPrice(newUserConfiguration);

        // Then
        assertNotNull(result);
        assertEquals(result.getPrice(),1111F);

        verify(carFeatureRepository, times(1)).findByIdAndFeatureType(any(),any());
        // Add assertions as needed for the calculated price and other properties
    }

    @Test
    public void givenUserConfigurationWhenCarClassInvalidThenThrowException() {
        // Given
        CarFeature carClass = CarFeature.builder().id(1L).featureType(FeatureTypes.CLASS).name("class").price(1F).build();
        CarFeature carType = CarFeature.builder().id(2L).featureType(FeatureTypes.TYPE).name("type").price(10F).build();
        CarFeature carMotor = CarFeature.builder().id(3L).featureType(FeatureTypes.MOTOR).name("motor").price(100F).build();
        CarFeature carColor = CarFeature.builder().id(4L).featureType(FeatureTypes.COLOR).name("color").price(1000F).build();
        UserConfiguration newUserConfiguration = UserConfiguration.builder()
                .carClass(carClass)
                .carType(carType)
                .carMotor(carMotor)
                .carColor(carColor)
                .carExtras(List.of())
                .build();

        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.CLASS)))
                .thenReturn(Optional.empty());
        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.TYPE)))
                .thenReturn(Optional.of(carType));
        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.MOTOR)))
                .thenReturn(Optional.of(carMotor));
        when(carFeatureRepository.findByIdAndFeatureType(anyLong(), eq(FeatureTypes.COLOR)))
                .thenReturn(Optional.of(carColor));

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> configurationService.getConfigurationPrice(newUserConfiguration));
    }

    @Test
    public void givenUserConfigurationWhenNoCarClassSetThenThrowException() {
        // Given
        CarFeature carType = CarFeature.builder().id(2L).featureType(FeatureTypes.TYPE).name("type").price(10F).build();
        CarFeature carMotor = CarFeature.builder().id(3L).featureType(FeatureTypes.MOTOR).name("motor").price(100F).build();
        CarFeature carColor = CarFeature.builder().id(4L).featureType(FeatureTypes.COLOR).name("color").price(1000F).build();
        UserConfiguration newUserConfiguration = UserConfiguration.builder()
                .carType(carType)
                .carMotor(carMotor)
                .carColor(carColor)
                .carExtras(List.of())
                .build();

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> configurationService.getConfigurationPrice(newUserConfiguration));
    }

}