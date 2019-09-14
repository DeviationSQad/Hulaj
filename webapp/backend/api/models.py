from django.db import models
from django.contrib.auth.models import AbstractUser
from django.conf import settings
from django.utils.translation import ugettext_lazy as _


class Scooter(models.Model):
    photo = models.FileField(blank=True, null=True)
    mark = models.CharField(max_length=100, blank=False, null=False)
    type = models.CharField(max_length=25, blank=False, null=False)
    wheel_size = models.IntegerField(blank=True, null=True)
    wheel_type = models.CharField(max_length=25, blank=True, null=True)


class Car(models.Model):
    mark = models.CharField(max_length=100, blank=True, null=True)
    type = models.CharField(max_length=25, blank=True, null=True)
    fuel_consumption_per_100 = models.IntegerField(blank=False, null=False)
    exhaust_amount = models.IntegerField(blank=True, null=True)


class User(AbstractUser):
    username = models.CharField(max_length=50, blank=True, null=True)
    email = models.EmailField(_('email address'), unique=True)

    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username', 'first_name', 'last_name']

    def __str__(self):
        return "{}".format(self.email)


class UserProfile(models.Model):
    user = models.OneToOneField(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name='profile')
    id_scooter = models.ForeignKey(Scooter, on_delete=models.CASCADE)
    id_car = models.ForeignKey(Car, on_delete=models.CASCADE)
    photo = models.FileField(blank=True, null=True)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    bio = models.CharField(max_length=300, default='', blank=True, null=True)
    date_of_birth = models.DateField(blank=True, null=True)
    country = models.CharField(max_length=50, blank=True, null=True)
    city = models.CharField(max_length=75, blank=True, null=True)
    points = models.PositiveIntegerField(default=0)


class Event(models.Model):
    creator = models.ForeignKey(User, on_delete=models.CASCADE)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    title = models.CharField(max_length=100)
    place_name = models.CharField(max_length=100)
    country = models.CharField(max_length=50)
    city = models.CharField(max_length=75)
    address = models.CharField(max_length=100)
    event_date = models.DateTimeField(blank=False, null=False)
    max_amount_of_people = models.IntegerField(blank=True)
    is_active = models.BooleanField(default=True)


class Post(models.Model):
    creator = models.ForeignKey(User, on_delete=models.CASCADE)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    post_type = models.CharField(max_length=20, blank=False, null=False)
    title = models.CharField(max_length=100)
    text = models.CharField(max_length=500, blank=False, null=False)


class Trace(models.Model):
    creator = models.ForeignKey(User, on_delete=models.CASCADE)
    add_date = models.DateTimeField(auto_now_add=True)
    mod_date = models.DateTimeField(auto_now_add=True)
    time_start = models.DateTimeField(blank=False, null=False)
    time_end = models.DateTimeField(blank=False, null=False)
    trace_length = models.FloatField(blank=False, null=False, default=0.0)
