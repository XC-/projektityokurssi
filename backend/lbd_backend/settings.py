# -*- coding: utf-8 -*-

import os
import mongoengine


BASE_DIR = os.path.dirname(os.path.dirname(__file__))

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = ')_7av^!cy(wfx=k#3*7x+(=j^fzv+ot^1@sh9s9t=8$bu@r(z$'

DEBUG = True

TEMPLATE_DEBUG = DEBUG

ALLOWED_HOSTS = ["*"]

INSTALLED_APPS = (
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
#    'django.contrib.staticfiles',
    'lbd_backend.LBD_REST_locationdata',
)

MIDDLEWARE_CLASSES = (
    'django.contrib.sessions.middleware.SessionMiddleware',
    'lbd_backend.middleware.cors.CorsMiddleware',
    'django.middleware.common.CommonMiddleware',
#    'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
)

ROOT_URLCONF = 'lbd_backend.urls'

WSGI_APPLICATION = 'lbd_backend.wsgi.application'

mongoengine.connect("lbd_backend", host="127.0.0.1:27017")

LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True

AUTHENTICATION_BACKENDS = (
                            'mongoengine.django.auth.MongoEngineBackend',
                            )
SESSION_ENGINE = 'mongoengine.django.sessions'

TEST_RUNNER = 'lbd_backend.LBD_REST_locationdata.testrunner.NoSQLTestRunner'