package com.pro100byte.profile.service

import com.pro100byte.exception.ServiceException
import com.pro100byte.profile.model.Profile
import com.pro100byte.profile.model.ProfileEdit
import com.pro100byte.profile.model.SkillTag
import com.pro100byte.profile.repository.ProfileRepository
import com.pro100byte.profile.repository.SkillTagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

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

    @Transactional
    fun editProfile(profileEdit: ProfileEdit): Profile {
        val profile = profileRepository.findByIdOrNull(profileEdit.id)
            ?: throw ServiceException("Couldnt find profile with id: ${profileEdit.id}", 404)

        profileEdit.avatar?.let {
            profile.avatar = it
        }
        profileEdit.firstName?.let {
            profile.firstName = it
        }
        profileEdit.lastName?.let {
            profile.lastName = it
        }
        profileEdit.patronymic?.let {
            profile.patronymic = it
        }
        profileEdit.birthDate?.let {
            profile.birthDate = it.toLong()
        }
        profileEdit.location?.let {
            profile.location = it
        }
        profileEdit.passportSerial?.let {
            profile.passportSerial = it
        }
        profileEdit.passportNumber?.let {
            profile.passportNumber = it
        }
        profileEdit.inn?.let {
            profile.inn = it
        }
        profileEdit.cv?.let {
            profile.cv = it
        }
        profileEdit.video?.let {
            profile.video = it
        }
        profileEdit.skillTags?.let {
            profile.rawSkillTags = it.joinToString(separator = ",")
        }

        profileEdit.skillTags?.distinct()?.map {
            SkillTag().apply {
                this.skillTag = it.lowercase()
                this.profiles?.add(profile)
            }
        }?.let {
            profile.skillTags?.addAll(it)
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
