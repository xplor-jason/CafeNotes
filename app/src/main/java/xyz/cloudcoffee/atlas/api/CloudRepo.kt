package xyz.cloudcoffee.atlas.api

import xyz.cloudcoffee.atlas.api.repo.NotesRepo

interface CloudRepo {
    val notes : NotesRepo
}