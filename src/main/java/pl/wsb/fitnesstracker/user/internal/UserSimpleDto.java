package pl.wsb.fitnesstracker.user.internal;

/** Lightweight DTO — only id, firstName, lastName. */
record UserSimpleDto(Long id, String firstName, String lastName) {}