package com.pro100byte.profile.service

import com.pro100byte.dto.ProfileEditView
import com.pro100byte.exception.ServiceException
import com.pro100byte.profile.model.Profile
import com.pro100byte.profile.repository.ProfileRepository
import com.pro100byte.profile.repository.SkillTagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val skillTagRepository: SkillTagRepository,
) {

    fun createInitialProfile(): Long {
        val profile = Profile()
        return profileRepository.save(profile).id
            ?: throw ServiceException.smthWentWrong()
    }

    fun editProfile(profileEditView: ProfileEditView): Profile {
        val profile = profileRepository.findByIdOrNull(profileEditView.id.toLong())
            ?: throw ServiceException("Couldnt find profile with id: ${profileEditView.id.toLong()}", 404)

        profileEditView.avatar?.let {
            profile.avatar = it
        }
        profileEditView.firstName?.let {
            profile.firstName = it
        }
        profileEditView.lastName?.let {
            profile.lastName = it
        }
        profileEditView.patronymic?.let {
            profile.patronymic = it
        }
        profileEditView.birthDate?.let {
            profile.birthDate = it.toLong()
        }
        profileEditView.location?.let {
            profile.location = it
        }
        profileEditView.passportSerial?.let {
            profile.passportSerial = it
        }
        profileEditView.passportNumber?.let {
            profile.passportNumber = it
        }
        profileEditView.inn?.let {
            profile.inn = it
        }
        profileEditView.cv?.let {
            profile.cv = it
        }
        profileEditView.video?.let {
            profile.video = it
        }

        return profileRepository.save(profile)
    }

    fun findById(id: Long): Profile {
        return profileRepository.findByIdOrNull(id)
            ?: throw ServiceException("Couldnt find profile with id: $id", 404)
    }

    fun findMyProfile(id: Long): Profile {
        return profileRepository.findByIdOrNull(id)
            ?: throw ServiceException("Couldnt find your profile", 404)
    }
}
