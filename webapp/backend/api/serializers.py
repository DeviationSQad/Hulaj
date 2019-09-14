from rest_framework.serializers import ModelSerializer
from .models import User, UserProfile, Event, Post, Track
from rest_auth.models import TokenModel


class UserProfileSerializer(ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('photo', 'bio', 'date_of_birth', 'country', 'city', 'points')


class UserSerializer(ModelSerializer):
    profile = UserProfileSerializer(required=True)

    class Meta:
        model = User
        fields = ('id', 'email', 'first_name', 'last_name', 'password', 'profile')
        extra_kwargs = {'password': {'write_only': True}}


class TokenSerializer(ModelSerializer):
    """
    Added user to login ourput in rest-auth
    """
    user = UserSerializer(many=False, read_only=True)  # prepare user

    class Meta:
        model = TokenModel
        fields = ('key', 'user')  # Return key and user


class TrackSerializer(ModelSerializer):
    class Meta:
        model = Track
        fields = ('id', 'id_user', 'time_start', 'time_end', 'duration', 'track_length')


class EventSerializer(ModelSerializer):
    class Meta:
        model = Event
        fields = ('id', 'id_user', 'tile', 'place_name', 'country', 'city', 'address', 'event_date', 'max_amount_of_people', 'is_acitve')


class PostSerializer(ModelSerializer):
    class Meta:
        model = Post
        fields = ('id', 'id_user', 'post_type', 'title', 'text')
